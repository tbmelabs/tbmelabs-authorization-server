package ch.tbmelabs.authorizationserver.web.ssr;

import ch.tbmelabs.authorizationserver.ssr.AngularUniversalRenderEngine;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViewController {

  private AngularUniversalRenderEngine angularUniversalRenderEngine;

  public ViewController(AngularUniversalRenderEngine angularUniversalRenderEngine) {
    this.angularUniversalRenderEngine = angularUniversalRenderEngine;
  }

  @GetMapping("/")
  public Callable<String> indexView() throws InterruptedException, ExecutionException {
    return () -> angularUniversalRenderEngine.renderUri("/").get();
  }

  @GetMapping("/signin")
  public Callable<String> signinView() throws InterruptedException, ExecutionException {
    return () -> angularUniversalRenderEngine.renderUri("/signin").get();
  }

  @GetMapping("/signup")
  public Callable<String> signupView() throws InterruptedException, ExecutionException {
    return () -> angularUniversalRenderEngine.renderUri("/signup").get();
  }

  @GetMapping("/oauth/confirm_access")
  public Callable<String> oauthConfirmAccessView() throws InterruptedException, ExecutionException {
    return () -> angularUniversalRenderEngine.renderUri("/oauth/confirm_access").get();
  }
}
