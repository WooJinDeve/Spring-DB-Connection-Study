package hello.springtx.order;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderServiceTest {

    @Autowired OderService oderService;
    @Autowired OrderRepository orderRepository;

    @Test
    void order() throws NotEnoughMoneyException {
        //given
        Order order = new Order();
        order.setUsername("정상");

        //when
        oderService.order(order);

        //than
        Order findOrder = orderRepository.findById(order.getId()).get();
        assertThat(findOrder.getPayStatus()).isEqualTo("완료");
    }


    @Test
    void runtimeException() throws NotEnoughMoneyException {
        //given
        Order order = new Order();
        order.setUsername("예외");

        //when
        Assertions.assertThatThrownBy(() -> oderService.order(order))
                .isInstanceOf(RuntimeException.class);

        //than
        Optional<Order> orderOptional = orderRepository.findById(order.getId());
        assertThat(orderOptional.isEmpty()).isTrue();
    }


    @Test
    void bizException(){
        //given
        Order order = new Order();
        order.setUsername("잔고부족");

        //when
        try {
            oderService.order(order);
        } catch (NotEnoughMoneyException e) {
            log.info("고객에게 잔고 부족을 알리고 별도의 계죄로 입급하도록 안내");
        }

        //than
        Order findOrder = orderRepository.findById(order.getId()).get();
        assertThat(findOrder.getPayStatus()).isEqualTo("대기");
    }
}