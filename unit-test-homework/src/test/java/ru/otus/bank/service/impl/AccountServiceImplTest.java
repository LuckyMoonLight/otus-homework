package ru.otus.bank.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.bank.dao.AccountDao;
import ru.otus.bank.entity.Account;
import ru.otus.bank.entity.Agreement;
import ru.otus.bank.service.exception.AccountException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {
    @Mock
    AccountDao accountDao;

    @InjectMocks
    AccountServiceImpl accountServiceImpl;

    @Captor
    ArgumentCaptor<Account> accountArgumentCaptor;

    @Test
    public void testTransfer() {
        Account sourceAccount = new Account();
        sourceAccount.setAmount(new BigDecimal(100));

        Account destinationAccount = new Account();
        destinationAccount.setAmount(new BigDecimal(10));

        when(accountDao.findById(eq(1L))).thenReturn(Optional.of(sourceAccount));
        when(accountDao.findById(eq(2L))).thenReturn(Optional.of(destinationAccount));

        accountServiceImpl.makeTransfer(1L, 2L, new BigDecimal(10));

        assertEquals(new BigDecimal(90), sourceAccount.getAmount());
        assertEquals(new BigDecimal(20), destinationAccount.getAmount());
    }

    @Test
    public void testSourceNotFound() {
        when(accountDao.findById(any())).thenReturn(Optional.empty());

        AccountException result = assertThrows(AccountException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                accountServiceImpl.makeTransfer(1L, 2L, new BigDecimal(10));
            }
        });
        assertEquals("No source account", result.getLocalizedMessage());
    }


    @Test
    public void testTransferWithVerify() {
        Account sourceAccount = new Account();
        sourceAccount.setAmount(new BigDecimal(100));
        sourceAccount.setId(1L);

        Account destinationAccount = new Account();
        destinationAccount.setAmount(new BigDecimal(10));
        destinationAccount.setId(2L);

        when(accountDao.findById(eq(1L))).thenReturn(Optional.of(sourceAccount));
        when(accountDao.findById(eq(2L))).thenReturn(Optional.of(destinationAccount));

        ArgumentMatcher<Account> sourceMatcher =
                argument -> argument.getId().equals(1L) && argument.getAmount().equals(new BigDecimal(90));

        ArgumentMatcher<Account> destinationMatcher =
                argument -> argument.getId().equals(2L) && argument.getAmount().equals(new BigDecimal(20));

        accountServiceImpl.makeTransfer(1L, 2L, new BigDecimal(10));

        verify(accountDao).save(argThat(sourceMatcher));
        verify(accountDao).save(argThat(destinationMatcher));
        }

    @Test
    public void addAccount() {
        Account expect = new Account();
        expect.setType(11);
        expect.setAgreementId(100L);
        expect.setNumber("num");
        expect.setAmount(BigDecimal.ZERO);

        Agreement agreement = new Agreement();
        agreement.setId(100L);

        accountServiceImpl.addAccount(agreement, "num", 11, BigDecimal.ZERO);

        verify(accountDao, times(1)).save(accountArgumentCaptor.capture());
        assertThat(expect).usingRecursiveComparison().isEqualTo(accountArgumentCaptor.getValue());
    }

    @Test
    public void charge() {
        Account account = new Account();
        account.setAmount(BigDecimal.TWO);

        Account expect = new Account();
        expect.setAmount(BigDecimal.ONE);

        when(accountDao.findById(1L)).thenReturn(Optional.of(account));

        accountServiceImpl.charge(1L, BigDecimal.ONE);

        verify(accountDao, times(1)).save(accountArgumentCaptor.capture());
        assertThat(expect).usingRecursiveComparison().isEqualTo(accountArgumentCaptor.getValue());
    }

    @Test
    public void chargeThrows() {
        when(accountDao.findById(anyLong())).thenReturn(Optional.empty());

        AccountException actualException = assertThrows(AccountException.class, () -> accountServiceImpl.charge(1L, BigDecimal.ONE));

        assertEquals("No source account", actualException.getLocalizedMessage());
    }

}
