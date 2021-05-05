package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect //aop를 쓸 수 있음.
@Component //컴포넌트 스캔
public class TimeTraceAop {

//    @Around("execution(* hello.hellospring.service..*(..))") //service 하위의 메소드 모두에 적용함.
    @Around("execution(* hello.hellospring..*(..))") //hellospring 하위의 메소드 모두에 적용함.
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try{
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;

            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");

        }
    }
}
