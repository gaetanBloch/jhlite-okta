package com.mycompany.myapp.shared.kipe.application;

import static org.assertj.core.api.Assertions.*;

import ch.qos.logback.classic.Level;
import java.util.List;
import com.mycompany.myapp.Logs;
import com.mycompany.myapp.LogsSpy;
import com.mycompany.myapp.LogsSpyExtension;
import com.mycompany.myapp.UnitTest;
import com.mycompany.myapp.shared.error.domain.MissingMandatoryValueException;
import com.mycompany.myapp.shared.kipe.domain.KipeDummy;
import com.mycompany.myapp.shared.kipe.domain.KipeDummyChild;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

@UnitTest
@ExtendWith({ MockitoExtension.class, LogsSpyExtension.class })
class AccessEvaluatorTest {

  @Mock
  private Authentication authentication;

  @Logs
  private LogsSpy logs;

  @Test
  void shouldNotBuildWithoutObjectChecker() {
    assertThatThrownBy(() -> new AccessEvaluator(List.of())).isExactlyInstanceOf(MissingMandatoryValueException.class);
  }

  @Test
  void shouldResolveOnDefaultCheckerForNullObject() {
    AccessEvaluator canEvaluator = new AccessEvaluator(List.of(new ObjectAccessChecker()));

    boolean can = canEvaluator.can(authentication, "action", null);

    assertThat(can).isFalse();
    logs.shouldHave(Level.WARN, "using default");
  }

  @Test
  void shouldResolveOnDefaultCheckerForUnknownType() {
    AccessEvaluator canEvaluator = new AccessEvaluator(List.of(new ObjectAccessChecker()));

    boolean can = canEvaluator.can(authentication, "action", "yo");

    assertThat(can).isFalse();
    logs.shouldHave(Level.WARN, "using default");
  }

  @Test
  void shouldGetMatchingEvaluator() {
    AccessEvaluator canEvaluator = new AccessEvaluator(List.of(new ObjectAccessChecker(), new KipeDummyAccessChecker()));

    assertThat(canEvaluator.can(authentication, "action", new KipeDummy("authorized"))).isTrue();
  }

  @Test
  void shouldGetMatchingEvaluatorForChildClass() {
    AccessEvaluator canEvaluator = new AccessEvaluator(List.of(new ObjectAccessChecker(), new KipeDummyAccessChecker()));

    assertThat(canEvaluator.can(authentication, "action", new KipeDummyChild("authorized"))).isTrue();
    logs.shouldHave(Level.INFO, "evaluator", 1);
  }
}
