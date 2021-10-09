package com.abc;

import com.abc.local.LocalUser;
import com.abc.local.LocalUserRepository;
import com.abc.remote.RemoteUser;
import com.abc.remote.RemoteUserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.transaction.Transactional;

@SpringBootApplication
@Transactional
public class MultiDatasourceDemo {
    public static void main(String... args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(MultiDatasourceDemo.class, args);
        LocalUserRepository localUserRepository = (LocalUserRepository) ctx.getBean("localUserRepository");
        RemoteUserRepository remoteUserRepository = (RemoteUserRepository) ctx.getBean("remoteUserRepository");

        LocalUser user = new LocalUser();
        user.setId("1");
        user.setAge(10);
        user.setName("LocalUser");
        localUserRepository.saveAndFlush(user);

        RemoteUser remoteUser = new RemoteUser();
        remoteUser.setId("2");
        remoteUser.setName(user.getName());
        remoteUser.setAge(user.getAge());
        remoteUserRepository.save(remoteUser);
        System.out.println("All saved.");
    }
}
