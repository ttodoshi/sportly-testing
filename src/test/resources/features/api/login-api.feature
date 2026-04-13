Feature: Логин в систему

  @api
  Scenario Outline: login_success
    When Отправить запрос на "/api/v1/auth/login" c хедером Subsystem: "<subsystem>" с телом "<email>" и "<password>"
    Then Проверить, что код ответа 200
    Then Проверить, что вернулось поле access_token

    Examples:
      | subsystem | email             | password  |
      | web       | admin@example.com | Admin123! |
      | web       | gym@example.com   | password  |

  @api
  Scenario Outline: login_invalid_data
    When Отправить запрос на "/api/v1/auth/login" c хедером Subsystem: "<subsystem>" с телом "<email>" и "<password>"
    Then Проверить, что код ответа 401
    Then Проверить, что вернулось сообщение об ошибке "Неверный email или пароль"

    Examples:
      | subsystem | email             | password |
      | web       | admin@example.com | 123      |
      | web       | gym@example.com   | 123      |

