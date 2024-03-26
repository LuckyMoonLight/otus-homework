package ru.otus.bank.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.otus.bank.dao.AccountDao;
import ru.otus.bank.entity.Account;
import ru.otus.bank.entity.Agreement;
import ru.otus.bank.service.exception.AccountException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class PaymentProcessorImplWithSpyTest {

    @Mock
    AccountDao accountDao;

    @Spy
    @InjectMocks
    AccountServiceImpl accountService;

    @InjectMocks
    PaymentProcessorImpl paymentProcessor;

    @BeforeEach
    public void init() {
        paymentProcessor = new PaymentProcessorImpl(accountService);
    }

    @Test
    public void testTransfer() {
        Agreement sourceAgreement = new Agreement();
        sourceAgreement.setId(1L);

        Agreement destinationAgreement = new Agreement();
        destinationAgreement.setId(2L);

        Account sourceAccount = new Account();
        sourceAccount.setAmount(BigDecimal.TEN);
        sourceAccount.setType(0);
        sourceAccount.setId(10L);

        Account destinationAccount = new Account();
        destinationAccount.setAmount(BigDecimal.ZERO);
        destinationAccount.setType(0);
        destinationAccount.setId(20L);

        doReturn(List.of(sourceAccount)).when(accountService).getAccounts(argThat(new ArgumentMatcher<Agreement>() {
            @Override
            public boolean matches(Agreement argument) {
                return argument != null && argument.getId() == 1L;
            }
        }));

        doReturn(List.of(destinationAccount)).when(accountService).getAccounts(argThat(new ArgumentMatcher<Agreement>() {
            @Override
            public boolean matches(Agreement argument) {
                return argument != null && argument.getId() == 2L;
            }
        }));

        when(accountDao.findById(10L)).thenReturn(Optional.of(sourceAccount));
        when(accountDao.findById(20L)).thenReturn(Optional.of(destinationAccount));
//        when(accountDao.findById(30L)).thenReturn(Optional.of(destinationAccount));

        paymentProcessor.makeTransfer(sourceAgreement, destinationAgreement,
                0, 0, BigDecimal.ONE);

        assertEquals(new BigDecimal(9), sourceAccount.getAmount());
        assertEquals(BigDecimal.ONE, destinationAccount.getAmount());

    }

    @Test
    public void makeTransferWithComission() {
        Agreement sourceAgreement = new Agreement();
        sourceAgreement.setId(1L);

        Agreement destinationAgreement = new Agreement();
        destinationAgreement.setId(2L);

        Account sourceAccount = new Account();
        sourceAccount.setAmount(BigDecimal.TEN);
        sourceAccount.setType(0);
        sourceAccount.setId(10L);

        Account destinationAccount = new Account();
        destinationAccount.setAmount(BigDecimal.ZERO);
        destinationAccount.setType(0);
        destinationAccount.setId(20L);

        doReturn(List.of(sourceAccount)).when(accountService).getAccounts(argThat(new ArgumentMatcher<Agreement>() {
            @Override
            public boolean matches(Agreement argument) {
                return argument != null && argument.getId() == 1L;
            }
        }));

        doReturn(List.of(destinationAccount)).when(accountService).getAccounts(argThat(new ArgumentMatcher<Agreement>() {
            @Override
            public boolean matches(Agreement argument) {
                return argument != null && argument.getId() == 2L;
            }
        }));

        when(accountDao.findById(10L)).thenReturn(Optional.of(sourceAccount));
        when(accountDao.findById(20L)).thenReturn(Optional.of(destinationAccount));

        paymentProcessor.makeTransferWithComission(sourceAgreement, destinationAgreement,
                0, 0, BigDecimal.ONE, BigDecimal.ONE.negate());

        verify(accountService, times(1)).makeTransfer(10L, 20L, BigDecimal.ONE);
        verify(accountService, times(1)).charge(10L, BigDecimal.ONE);
        assertEquals(new BigDecimal(8), sourceAccount.getAmount());
        assertEquals(BigDecimal.ONE, destinationAccount.getAmount());
    }

    @Test
    public void makeTransferWithComissionNotFoundSourceByType() {
        Agreement sourceAgreement = new Agreement();
        sourceAgreement.setId(1L);

        Agreement destinationAgreement = new Agreement();
        destinationAgreement.setId(2L);

        Account sourceAccount = new Account();
        sourceAccount.setAmount(BigDecimal.TEN);
        sourceAccount.setType(1);
        sourceAccount.setId(10L);


        doReturn(List.of(sourceAccount)).when(accountService).getAccounts(argThat(new ArgumentMatcher<Agreement>() {
            @Override
            public boolean matches(Agreement argument) {
                return argument != null && argument.getId() == 1L;
            }
        }));

        AccountException actualException = assertThrows(AccountException.class,
                () -> paymentProcessor.makeTransferWithComission(sourceAgreement, destinationAgreement,
                        0, 0, BigDecimal.ONE, BigDecimal.ONE.negate()));

        assertEquals("Account not found", actualException.getLocalizedMessage());
    }

    @Test
    public void makeTransferWithComissionNotFoundDestinationByType() {
        Agreement sourceAgreement = new Agreement();
        sourceAgreement.setId(1L);

        Agreement destinationAgreement = new Agreement();
        destinationAgreement.setId(2L);

        Account sourceAccount = new Account();
        sourceAccount.setAmount(BigDecimal.TEN);
        sourceAccount.setType(0);
        sourceAccount.setId(10L);

        Account destinationAccount = new Account();
        destinationAccount.setAmount(BigDecimal.ZERO);
        destinationAccount.setType(1);
        destinationAccount.setId(20L);
        doReturn(List.of(sourceAccount)).when(accountService).getAccounts(argThat(new ArgumentMatcher<Agreement>() {
            @Override
            public boolean matches(Agreement argument) {
                return argument != null && argument.getId() == 1L;
            }
        }));

        doReturn(List.of(destinationAccount)).when(accountService).getAccounts(argThat(new ArgumentMatcher<Agreement>() {
            @Override
            public boolean matches(Agreement argument) {
                return argument != null && argument.getId() == 2L;
            }
        }));

        AccountException actualException = assertThrows(AccountException.class,
                () -> paymentProcessor.makeTransferWithComission(sourceAgreement, destinationAgreement,
                        0, 0, BigDecimal.ONE, BigDecimal.ONE.negate()));

        assertEquals("Account not found", actualException.getLocalizedMessage());
    }

}
