# Комментарии к реализации от студента
Постарался сделать интересно, но не усложнял особо :)
1. Для каждого этажа - 2 генератора (для очереди вверх и очереди вниз), у каждого генератора - своя интенсивность создания людей
2. Для возможности создания разных контроллеров лифта с разной логикой разброса вызовов сделана иерархия классов с интерфейсом ElevatorController во главе
3. Алгоритм контроллера по выбору лифта: свободный лифт -> попутный еще не проехавший нужный этаж с минимальным кол-вом людей -> лифт с минимальным кол-вом людей
4. Алгоритм лифта по выбору этажа и движению. У лифта есть номер этажа, на который он едет в данный момент. Сначала - проверка, не появился ли новый вызов поближе (допускается и текущий этаж) -> обмен людей, если лифт на нужном этаже -> поиск ближайшего вызова (здесь текущий этаж уже не допускается) -> движение
# Итоговый проект
1. Есть многоэтажное здание (этажность конфигурируема). В здании есть лифты (количество конфигурируемо). На каждом этаже есть кнопки вызова “вверх” и “вниз” (общие для всех лифтов) На каждом этаже появляются люди (рандомной массы), которые хотят ехать на другой этаж (рандомный). Интенсивность генерации людей конфигурируема
2. У каждого лифта есть грузоподъемность, скорость и скорость открытия/закрытия дверей.
3. У человека есть масса и этаж, на который ему нужно.
4. Люди стоят в очереди на засадку в лифты (одна очередь вверх, одна вниз) не нарушая её. Приехав на нужный этаж, человек исчезает.
**Задание.** Необходимо реализовать непрерывно работающее приложение (люди появляются, вызывают лифт и едут на нужный этаж) используя многопоточность (Thread, wait, notify, sleep).
По желанию можно использовать `java.util.concurrent`. Также описать выбранный алгоритм текстом (кратко).
- тесты, maven, логгирование;
- реализовать сбор статистики (сколько людей перевезено каждым лифтом, с каких этажей и на какие этажи);
- логировать основные события системы (чтобы по логам можно было следить за тем, что происходит);
- рекомендация: реализовать логику управления лифтами и покрыть её тестами без потоков, а потом подключить многопоточность. 
## Комментарии к условию
### Здание
В условии везде в местах, где указано конфигурируемо, не имеется в виду, что должно быть конфигурируемо обязательно через `property file` или еще как-нибудь. Просто, например, `Building` будет принимать параметр количество этажей, например, в здании есть (а это многоэтажное здание) сорок этажей. 
### Лифты
Чаще всего такие лифты присутствуют в офисных зданиях, не в жилых. Когда на этаже есть кнопки вызова вверх или вниз. Как мы понимаем вызов лифта может происходить по разному. В жилых домах обычно ставят, что лифт когда едет наверх, он не останавливается, а когда едет вниз он забирает всех на своем пути. Но у нас будут кнопочки “вверх” и “вниз”. И на каждом этаже есть только две кнопки. Эти кнопки являются общими для всех лифтов. Думаю вы замечали, что бывает так: приходишь, а там четыре лифта и кнопки у каждого свои “вверх” и “вниз”. Это не наш случай. У нас приходишь и нажимаешь кнопочку “вверх” если тебе нужно вверх и приезжает какой-то лифт, один из четырех, и ты в него заходишь и едешь вверх.
#### Параметры
У каждого лифта есть следующие параметры: 
- грузоподъемность, которая будет измеряйся в килограммах.
- скорость, например он проезжает два этажа в секунду, или один этаж в секунду, или один этаж за две секунды. Эта скорость может быть конфигурируема. Вы можете сделать в своём здании пять лифтов и у каждого будет своя скорость.
- скорость открытия/закрытия дверей, этот параметр тоже может быть конфигурируемый, но не обязательно задавать его разным для разных лифтов. Один лифт быстро двери открывает, а второй медленно. Это перебор. Просто нужно сделать так, чтобы этот параметр было удобно менять.
### Люди
На каждом этаже случайным образом появляются люди, которые хотят куда-то ехать. Люди эти случайной массы.
Приехав на нужных этаж человек исчезает, т.е. он приехал на пятый и куда-то исчезает. Зашел к себе в кабинет и всё, больше его нет. Ну и люди честные. Они когда приходят и видят очередь, то спрашивают: “А кто тут наверх сейчас крайний в очереди?”. На первом и на последнем этажах, понятно, будет только одна очередь. А на всех промежуточных этажах будет по две очереди.
#### Генератор толстых и тонких людей
Интенсивность генерации людей также конфигурируема, как и все остальное. Вам нужно сделать какой-то класс, объект, который будет называться `PeopleSpawn` и будет генерировать людей. Т.е. он сгенерирвал толстого человека на текущем этаже (на каждом этаже свой генератор людей). На первом этаже генератор, который много людей генерирует. На третьем этаже возможно мало или на втором мало. Тут нет строгих требований. Вы можете сделать генераторы для очереди вверх и очереди вниз отдельно. Либо сделать общий генератор, который будет рандомно добавлять людей в обе очереди.
По поводу использования `java.util.concurrent`. В вашем задании было прочитать, там в статье это есть про `java.util.concurrent`. Это более высокоуровневое понятие. Мы про что-то поговорим, но опять же не про все. Поэтому лучше прочитайте дополнительно.
### Что делать?
Вот и все задание. Нужно это дело сделать, чтобы работало непрерывно. Вы инициализируете здание, вы инициализируете лифты, вы инициализируете рандомное появление людей.
### Сбор статистики.
Про сбор статистики возникают обычно вопросы у людей, если что-то такое написать. Нужно сделать какой-то `Storage`, в котором будет известна статистика. Что на таком-то этаже, столько-то приехало людей, на таких-то лифтах. Чтобы можно было потом посмотреть посчитать.
Логирование. Тут можно было бы сделать визуализацию, консольную визуализацию. Просто это не нужно. Можно было бы нарисовать в консоли этажи и по ним бы ехали лифты. Или не консольную, а другую какую-то визуализацию. Но наше задание не про то совсем. У нас акцент на многопоточность плюс, и даже больше чем многопоточность, на дизайн классов, на организацию кода. Не забывайте писать пакеты и прочие вещи.
Также я бы хотел дать рекомендацию, про это мы еще поговорим на последнем занятии. Лучше реализовать логику управления лифтами, я тут не сильно вдаюсь в подробности, и покрыть её тестами. Получается лифты будут потоками, т.е. поток в данном случае будет выполнять задачу не какого-то распараллеливания, здесь у нас вообще получается какая-то модель, где система живет своей жизнью. 
Потоки здесь будут для того, чтобы каждый лифт, каждый подвижный товарищ здесь. Кто у нас здесь подвижный? Кто у нас живет своей жизнью? Лифты живут своей жизнью. И люди, наверное, не обязательно делать, чтобы они жили своей жизнью. Потому что они, в принципе, ничего не делают. У них нету `life-cycle`. Они появились, они сели в лифт. Заниматься такой имитацией, чтобы человек топал и садился в лифт, нам не надо. Просто можно сделать, чтобы наш лифт засасывал нужное количество людей из соответствующей очереди. Ну и причем логика может быть какая? Если там первым стоит в очереди очень толстый человек, и он уже не влазит в лифт, то понятное дело, что можно пройтись по очереди на посадку и всосать более худеньких. Которые всё еще помещаются в лифт. Т.е. как обычно. “Ой, я уже не влезу, у меня вещей много, проходите вы.” Здесь такое тоже можно реализовать.
### Лифт-зомби
Но я бы хотел, что сказать. От этого не загоняйтесь. Делайте как получается. Просто в идеале лифты должны быть, я бы так рекомендовал, максимально глупыми, они должны выполнять только определенные задачи. Лифт, он такой как зомби. Ой, я проснулся. Кто я? Я лифт. Что мне делать? Мне нужно открывать двери. Ну, хорошо, я открываю двери. Всё, дальше. Так кто? Я лифт. Что со мной? Я открываю двери. У меня открыты двери. Что мне нужно делать дальше? Ну вот что-то такое. 
### Контроллер
При этом в здании, в котором есть больше одного лифта, скорее всего нужно реализовать какой-то контроллер, который будет смотреть, что если на пятом этаже нажата кнопка, то можно определить, что какой-то лифт сейчас находится без дела. Понятно, что у лифта могут быть разные состояния. И сказать, что вот ты, лифт, едь сюда, на пятый этаж и открывай двери. Что-то типа такого. 
Но эта задача почему интересная? Я пробовал эту задачку на людях с большим опытом, просто обсуждать варианты решения. У всех сразу загораются глаза, все хотят её сразу делать. Все хотят реализовывать всякие `state`-машины и прочее. Ну, т.е. очень можно по разному её реализовать. 
Что еще важно?
### Описание алгоритма
Описание алгоритма может быть в `javadoc`. Может быть просто кусок текста `readme.md`. Мне просто будет удобнее. Потому что здесь нету однозначного и единственно верного решения. Я здесь намеренно не пытаюсь сказать, как вам нужно реализовать то, чтобы эти лифты ездили. Вы должны написать пару предложений, чтобы мне было легче понимать. Потому что иногда получается логика хитрая. А я предвижу, что эта задача будет такая, когда посмотришь и ничего не понятно. Что-то куда-то ездит, а какая идея? 
Но опять же подумайте, чтобы алгоритм был Немножко эффективный. Т.е. какой может быть у этой задачи неэффективный алгоритм. Ну вот, например, самый неэффективный алгоритм, но самый простой и такой алгоритм, в принципе, может быть как такое решение первое. Но он совсем неэффективный. Это лифты беспрерывно ездят, например, у вас здание появляется, люди начинают собираться и нажимать кнопки вызова. И просто лифты поехали. Вверх. Сами по себе. И лифт проезжает этаж, если на этаже нажата кнопка, он просто останавливается, загружает до своего лимита и поехал дальше вверх. Если он проезжает этаж, на который его отправили пассажиры лифта, он тогда тоже высаживает людей. И даже если никому больше ехать не нужно лифты продолжают ездить вверх-вниз.  Такой `brute force` механизм иногда используется в программировании, но здесь не совсем нам подходит, т.е. нам нужна чуть более умная реализация. Чтобы при нажатии на кнопку происходил какой-то `action`. Если кто-то напишет такой алгоритм, самый простой, часть за алгоритм, конечно, много баллов не возьмет, это зависит еще от того, что там по коду будет дальше. Да, но при использовании вышеописанного примитивного алгоритма это будет совсем простая задачка. Тут посложнее надо.
Старайтесь помнить, когда будете делать про дизайн классов. Времени будет дано достаточно.
## Дальнейшие шаги
1. Нужно сделать итоговое задание (см. Ниже) и сдать его до **21 октября** включительно (**три недели**). А я потом еще до **31 октября** буду проверять. А также задания 1-3, если вы этого еще не сделали. В период с 18 по 21 октября будет открыта возможность сдать невыполненные в срок задания. Максимальный балл за просроченное выполнение заданий 1-3 будет уменьшен на 20%. 
2. Все кто сдадут 4 задания в срок до 21 октября, будут считаться успешно закончившими тренинг.
3. После окончания тренинга, мы будем приглашать людей на собеседование в порядке уменьшения итогового балла.