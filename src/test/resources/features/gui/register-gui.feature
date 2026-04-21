Feature: Регистрация в системе

  @ui
  @register
  Scenario Outline: register_success
    Given Открыта страница "/register"
    When В поле Фамилия ввести значение "<lastName>"
    And В поле Имя ввести значение "<firstName>"
    And В поле Отчество ввести значение "<patronymic>"
    And В поле Email при регистрации ввести значение "<email>"
    And В поле Пароль при регистрации ввести значение "<password>"
    Then Проверить, что кнопка Зарегистрироваться активна
    And Нажать кнопку Зарегистрироваться
    Then Проверить, что появился алерт с текстом "<result>"

    Examples:
      | lastName | firstName | patronymic | email            | password | result                  |
      | Залов    | Зал       | Залович    | gym4@example.com | password | Успешно зарегистрирован |

  @ui
  Scenario Outline: register_invalid_data
    Given Открыта страница "/register"
    When В поле Фамилия ввести значение "<lastName>"
    When В поле Имя ввести значение "<firstName>"
    When В поле Отчество ввести значение "<patronymic>"
    When В поле Email при регистрации ввести значение "<email>"
    And В поле Пароль при регистрации ввести значение "<password>"
    Then Проверить, что кнопка Зарегистрироваться активна
    And Нажать кнопку Зарегистрироваться
    Then Проверить, что появился алерт с текстом "<result>"

    Examples:
      | lastName | firstName | patronymic | email             | password  | result                                    |
      | Админов  | Админ     | Адинович   | admin@example.com | Admin123! | Пользователь с таким email уже существует |
      | Залов    | Зал       | Залович    | gym@example.com   | password  | Пользователь с таким email уже существует |

