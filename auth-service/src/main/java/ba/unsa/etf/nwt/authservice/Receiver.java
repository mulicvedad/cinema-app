package ba.unsa.etf.nwt.authservice;

import ba.unsa.etf.nwt.authservice.controllers.dto.UserAccountDTO;
import ba.unsa.etf.nwt.authservice.models.Role;
import ba.unsa.etf.nwt.authservice.models.UserAccount;
import ba.unsa.etf.nwt.authservice.services.RoleService;
import ba.unsa.etf.nwt.authservice.services.UserAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class Receiver {

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    RoleService roleService;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(
            value = "usersAuthQueue", durable = "false"),
            exchange = @Exchange(value = "users-exchange", type = "topic")
    ))
    public void registerUser(String user) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        UserAccountDTO obj = mapper.readValue(user, UserAccountDTO.class);
        Optional<Role> role  = roleService.find(obj.getRole());
        userAccountService.save(new UserAccount(obj.getUsername(),obj.getPasswordHash(),obj.getEmail(), role.get(), false));
    }

}
