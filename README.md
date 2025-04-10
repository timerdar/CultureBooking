# CultureBooking

## Предисловие
Проект выполнялся в качестве ВКР. Проект предназначен для бронирования мест (как в кино) на мероприятия в ДК УГНТУ. Особенность заключается в том, что места в зале должны делиться на сектора (чтобы студенты одного факультета сидели в одном месте). 
Есть второй [репозиторий](https://github.com/timerdar/culture-booking-frontend) с Frontend на React.

## Что делает проект
Backend для сервиса бронирования мест в концертном зале. Предоставляет фунционал для двух ролей: администратор и посетитель.
Для администратора: авторизация, создание мероприятия (с названием, описанием, датой проведения, секторами и местами, которые к секторам относятся), удаление, управление видимостью, контролем статистики по мероприятиям.
Для посетителей: просмотр мероприятий, бронирование места, загрузка билета, отмена и подтверждение использования билета.

## Почему проект полезен
Решает конкретную проблему для студентов и организаторов мероприятий. Проект можно адаптировать под свой концертный зал, поскольку на стороне backend нет установки и порядка мест. 

## Технологии
SpringBoot(+Data, Security), PostgreSQL.
