package ru.rsreu.java5.Runner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.task.support.ExecutorServiceAdapter;
import ru.rsreu.java5.logic.Loader;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class Logger {

    public static void main(String[] args) {
        Logger logger = new Logger();
        logger.run();
    }

    public void run() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
        Loader storageManager = (Loader) context.getBean("loader");
        storageManager.setDaemon(true);
        storageManager.start();
        ExecutorService executorService = (ExecutorService) context.getBean("executorService");
        try {
            executorService.invokeAll((List<Callable<Integer>>) context.getBean("shipList"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

}
