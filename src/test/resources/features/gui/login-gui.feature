Feature: Логин в систему

  @ui
  Scenario Outline: login_success
    Given Открыта страница "/login"
    When В поле Email ввести значение "<email>"
    And В поле Пароль ввести значение "<password>"
    Then Проверить, что кнопка Войти активна
    And Нажать кнопку Войти
    Then Проверить, что появился алерт с текстом "<result>"

    Examples:
      | email             | password  | result              |
      | admin@example.com | Admin123! | Успешно авторизован |
      | gym@example.com   | password  | Успешно авторизован |

  @ui
  Scenario Outline: login_invalid_data
    Given Открыта страница "/login"
    When В поле Email ввести значение "<email>"
    And В поле Пароль ввести значение "<password>"
    Then Проверить, что кнопка Войти активна
    And Нажать кнопку Войти
    Then Проверить, что появился алерт с текстом "<result>"

    Examples:
      | email             | password | result                    |
      | admin@example.com | 123      | Неверный email или пароль |
      | gym@example.com   | 123      | Неверный email или пароль |

