package com.example.expense_tracker.aspect;

import com.example.expense_tracker.entity.Account;
import com.example.expense_tracker.entity.Audit;
import com.example.expense_tracker.repository.AccountRepository;
import com.example.expense_tracker.repository.AuditRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class AccountAuditAspect {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AuditRepository auditRepository;

    @Around("execution(* com.example.expense_tracker.service.AccountService.updateAccount(..))")
    public Object auditAccountUpdate(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();

        UUID accountId = (UUID) args[0];
        BigDecimal newBalance = (BigDecimal) args[2];

        Account accountBefore = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        BigDecimal oldBalance = accountBefore.getBalance();

        Object result = pjp.proceed(); // Call the original method

        // Log only if balance is changed and newBalance is not null
        if (newBalance != null && oldBalance != null && oldBalance.compareTo(newBalance) != 0) {
            Audit audit = new Audit();
            audit.setAccount(accountBefore);
            audit.setOldBalance(oldBalance);
            audit.setNewBalance(newBalance);
            audit.setTimestamp(LocalDateTime.now());
            auditRepository.save(audit);
        }

        return result;
    }

}
