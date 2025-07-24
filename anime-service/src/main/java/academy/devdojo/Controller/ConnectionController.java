package academy.devdojo.Controller;


import academy.devdojo.Config.Connection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("v1/connections")
@RequiredArgsConstructor
@Slf4j
public class ConnectionController {

    private final Connection connectionMySql;

    @GetMapping
    public ResponseEntity<Connection> getConnections() {

        return ResponseEntity.ok(connectionMySql);
    }


}
