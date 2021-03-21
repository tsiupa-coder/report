package bigOperation;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import sample.Usedb;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;

public class Operation {

    SimpleStringProperty code;
    SimpleStringProperty name;
    SimpleDoubleProperty strDeb;
    SimpleDoubleProperty strCred;
    SimpleDoubleProperty deb;
    SimpleDoubleProperty cred;
    SimpleDoubleProperty endDeb;
    SimpleDoubleProperty endCred;

    String code1;

    HashMap<String, String> m = new HashMap<>();

    public Operation(String code, Double strDeb, Double strCred, Double deb, Double cred, Double endDeb, Double endCred) {
        this.code = new SimpleStringProperty(code);
        this.strDeb = new SimpleDoubleProperty(strDeb);
        this.strCred = new SimpleDoubleProperty(strCred);
        this.deb = new SimpleDoubleProperty(deb);
        this.cred = new SimpleDoubleProperty(cred);
        this.endDeb = new SimpleDoubleProperty(endDeb);
        this.endCred = new SimpleDoubleProperty(endCred);
        initializeMap();
    }

    public Operation(String code, double strDeb, double strCred, double deb, double cred) {

        this.code = new SimpleStringProperty(code);
        this.strDeb = new SimpleDoubleProperty(strDeb);
        this.strCred = new SimpleDoubleProperty(strCred);
        this.deb = new SimpleDoubleProperty(deb);
        this.cred = new SimpleDoubleProperty(cred);
        code1 = code;
        endValue();
        initializeMap();
    }

    private void initializeMap() {

        m.put("10", "Основні засоби");
        m.put("101", "Земельні ділянки");
        m.put("102", "Капітальні витрати на поліпшення земель");
        m.put("103", "Будинки та споруди");
        m.put("104", "Машини та обладнання");
        m.put("105", "Транспортні засоби");
        m.put("106", "Інструменти, прилади та інвентар");
        m.put("107", "Робоча і продуктивна худоба");
        m.put("108", "Багаторічні насадження");
        m.put("109", "Інші основні засоби");
        m.put("11", "Інші необоротні матеріальні активи");
        m.put("111", "Бібліотечні фонди");
        m.put("112", "Малоцінні необоротні матеріальні активи");
        m.put("113", "Тимчасові (нетитульні) споруди");
        m.put("114", "Природні ресурси");
        m.put("115", "Інвентарна тара");
        m.put("116", "Предмети прокату");
        m.put("117", "Інші необоротні матеріальні активи");
        m.put("12", "Нематеріальні активи");
        m.put("121", "Права користування природними ресурсами");
        m.put("122", "Права користування майном");
        m.put("123", "Права на знаки для товарів і послуг");
        m.put("124", "Права на об'єкти промислової власності");
        m.put("125", "Авторські та суміжні з ними права");
        m.put("127", "Інші нематеріальні активи");
        m.put("13", "Знос (амортизація) необоротних активів");
        m.put("131", "Знос основних засобів");
        m.put("132", "Знос інших необоротних матеріальних активів");
        m.put("133", "Накопичена амортизація нематеріальних активів");
        m.put("14", "Довгострокові фінансові інвестиції");
        m.put("141", "Інвестиції пов'язаним сторонам за методом обліку участі в капіталі");
        m.put("142", "Інші інвестиції пов'язаним сторонам");
        m.put("143", "Інвестиції непов'язаним сторонам");
        m.put("15", "Капітальні інвестиції");
        m.put("151", "Капітальне будівництво");
        m.put("152", "Придбання (виготовлення) основних засобів");
        m.put("153", "Придбання (виготовлення) інших необоротних матеріальних активів");
        m.put("154", "Придбання (створення) нематеріальних активів");
        m.put("155", "Формування основного стада");
        m.put("16", "Довгострокова дебіторська заборгованість");
        m.put("161", "Заборгованість за майно, що передано у фінансову оренду");
        m.put("162", "Довгострокові векселі одержані");
        m.put("163", "Інша дебіторська заборгованість");
        m.put("17", "Відстрочені податкові активи");
        m.put("18", "Інші необоротні активи");
        m.put("19", "Гудвіл при придбанні");
        m.put("191", "Гудвіл");
        m.put("192", "Негативний гудвіл");
        m.put("20", "Виробничі запаси");
        m.put("201", "Сировина й матеріали");
        m.put("202", "Купівельні напівфабрикати та комплектуючі вироби");
        m.put("203", "Паливо");
        m.put("204", "Тара й тарні матеріали");
        m.put("205", "Будівельні матеріали");
        m.put("206", "Матеріали, передані в переробку");
        m.put("207", "Запасні частини");
        m.put("208", "Матеріали сільськогосподарського призначення");
        m.put("209", "Інші матеріали");
        m.put("21", "Тварини на вирощуванні та відгодівлі");
        m.put("211", "Молодняк тварин на вирощуванні");
        m.put("212", "Тварини на відгодівлі");
        m.put("213", "Птиця");
        m.put("214", "Звірі");
        m.put("215", "Кролі");
        m.put("216", "Сім'ї бджіл");
        m.put("217", "Доросла худоба, що вибракувана з основного стада");
        m.put("218", "Худоба, що прийнята від населення для реалізації");
        m.put("22", "Малоцінні та швидкозношувані предмети");
        m.put("23", "Виробництво");
        m.put("24", "Брак у виробництві");
        m.put("25", "Напівфабрикати");
        m.put("26", "Готова продукція");
        m.put("27", "Продукція сільськогосподарського виробництва");
        m.put("28", "Товари");
        m.put("281", "Товари на складі");
        m.put("282", "Товари в торгівлі");
        m.put("283", "Товари на комісії");
        m.put("284", "Тара під товарами");
        m.put("285", "Торгова націнка");
        m.put("30", "Каса");
        m.put("301", "Каса в національній валюті");
        m.put("302", "Каса в іноземній валюті");
        m.put("31", "Рахунки в банках");
        m.put("311", "Поточні рахунки в національній валюті");
        m.put("312", "Поточні рахунки в іноземній валюті");
        m.put("313", "Інші рахунки в банку в національній валюті");
        m.put("314", "Інші рахунки в банку в іноземній валюті");
        m.put("33", "Інші кошти");
        m.put("331", "Грошові документи в національній валюті");
        m.put("332", "Грошові документи в іноземній валюті");
        m.put("333", "Грошові кошти в дорозі в національній валюті");
        m.put("334", "Грошові кошти в дорозі в іноземній валюті");
        m.put("34", "Короткострокові векселі одержані");
        m.put("341", "Короткострокові векселі, одержані в національній валюті");
        m.put("342", "Короткострокові векселі, одержані в іноземній валюті");
        m.put("35", "Поточні фінансові інвестиції");
        m.put("351", "Еквіваленти грошових коштів");
        m.put("352", "Інші поточні фінансові інвестиції");
        m.put("36", "Розрахунки з покупцями та замовниками");
        m.put("361", "Розрахунки з вітчизняними покупцями");
        m.put("362", "Розрахунки з іноземними покупцями");
        m.put("363", "Розрахунки з учасниками ПФГ");
        m.put("37", "Розрахунки з різними дебіторами");
        m.put("371", "Розрахунки за виданими авансами");
        m.put("372", "Розрахунки з підзвітними особами");
        m.put("373", "Розрахунки за нарахованими доходами");
        m.put("374", "Розрахунки за претензіями");
        m.put("375", "Розрахунки за відшкодуванням завданих збитків");
        m.put("376", "Розрахунки за позиками членам кредитних спілок");
        m.put("377", "Розрахунки з іншими дебіторами");
        m.put("38", "Резерв сумнівних боргів");
        m.put("39", "Витрати майбутніх періодів");
        m.put("40", "Статутний капітал");
        m.put("41", "Пайовий капітал");
        m.put("42", "Додатковий капітал");
        m.put("421", "Емісійний дохід");
        m.put("422", "Інший вкладений капітал");
        m.put("423", "Дооцінка активів");
        m.put("424", "Безоплатно одержані необоротні активи");
        m.put("425", "Інший додатковий капітал");
        m.put("43", "Резервний капітал");
        m.put("44", "Нерозподілені прибутки (непокриті збитки)");
        m.put("441", "Прибуток нерозподілений");
        m.put("442", "Непокриті збитки");
        m.put("443", "Прибуток, використаний у звітному періоді");
        m.put("45", "Вилучений капітал");
        m.put("451", "Вилучені акції");
        m.put("452", "Вилучені вклади й паї");
        m.put("453", "Інший вилучений капітал");
        m.put("46", "Неоплачений капітал");
        m.put("47", "Забезпечення майбутніх витрат і платежів");
        m.put("471", "Забезпечення виплат відпусток");
        m.put("472", "Додаткове пенсійне забезпечення");
        m.put("473", "Забезпечення гарантійних зобов'язань");
        m.put("474", "Забезпечення інших витрат і платежів");
        m.put("48", "Цільове фінансування і цільові надходження");
        m.put("49", "Страхові резерви");
        m.put("491", "Технічні резерви");
        m.put("492", "Резерви із страхування життя");
        m.put("493", "Частка перестраховиків у технічних резервах");
        m.put("494", "Частка перестраховиків у резервах із страхування життя");
        m.put("495", "Результат зміни технічних резервів");
        m.put("496", "Результат зміни резервів із страхування життя");
        m.put("497", "Результат зміни резервів незароблених премій");
        m.put("498", "Результат зміни резервів збитків");
        m.put("Клас", "5. Довгострокові зобов'язання");
        m.put("50", "Довгострокові позики");
        m.put("501", "Довгострокові кредити банків у національній валюті");
        m.put("502", "Довгострокові кредити банків в іноземній валюті");
        m.put("503", "Відстрочені довгострокові кредити банків у національній валюті");
        m.put("504", "Відстрочені довгострокові кредити банків в іноземній валюті");
        m.put("505", "Інші довгострокові позики в національній валюті");
        m.put("506", "Інші довгострокові позики в іноземній валюті");
        m.put("51", "Довгострокові векселі видані");
        m.put("511", "Довгострокові векселі, видані в національній валюті");
        m.put("512", "Довгострокові векселі, видані в іноземній валюті");
        m.put("52", "Довгострокові зобов'язання за облігаціями");
        m.put("521", "Зобов'язання за облігаціями");
        m.put("522", "Премія за випущеними облігаціями");
        m.put("523", "Дисконт за випущеними облігаціями");
        m.put("53", "Довгострокові зобов'язання з оренди");
        m.put("531", "Зобов'язання з фінансової оренди");
        m.put("532", "Зобов'язання з оренди цілісних майнових комплексів");
        m.put("54", "Відстрочені податкові зобов'язання");
        m.put("55", "Інші довгострокові зобов'язання");
        m.put("Клас", "6. Поточні зобов'язання");
        m.put("60", "Короткострокові позики");
        m.put("601", "Короткострокові кредити банків у національній валюті");
        m.put("602", "Короткострокові кредити банків в іноземній валюті");
        m.put("603", "Відстрочені короткострокові кредити банків у національній валюті");
        m.put("604", "Відстрочені короткострокові кредити банків в іноземній валюті");
        m.put("605", "Прострочені позики в національній валюті");
        m.put("606", "Прострочені позики в іноземній валюті");
        m.put("61", "Поточна заборгованість за довгостроковими зобов'язаннями");
        m.put("611", "Поточна заборгованість за довгостроковими зобов'язаннями в національній валюті");
        m.put("612", "Поточна заборгованість за довгостроковими зобов'язаннями в іноземній валюті");
        m.put("62", "Короткострокові векселі видані");
        m.put("621", "Короткострокові векселі, видані в національній валюті");
        m.put("622", "Короткострокові векселі, видані в іноземній валюті");
        m.put("63", "Розрахунки з постачальниками та підрядниками");
        m.put("631", "Розрахунки з вітчизняними постачальниками");
        m.put("632", "Розрахунки з іноземними постачальниками");
        m.put("633", "Розрахунки з учасниками ПФГ");
        m.put("64", "Розрахунки за податками й платежами");
        m.put("641", "Розрахунки за податками");
        m.put("642", "Розрахунки за обов'язковими платежами");
        m.put("643", "Податкові зобов'язання");
        m.put("644", "Податковий кредит");
        m.put("65", "Розрахунки за страхування");
        m.put("651", "За пенсійним забезпеченням");
        m.put("652", "За соціальним страхуванням");
        m.put("653", "За страхуванням на випадок безробіття");
        m.put("654", "За індивідуальним страхуванням");
        m.put("655", "За страхуванням майна");
        m.put("66", "Розрахунки з оплати праці");
        m.put("661", "Розрахунки за заробітною платою");
        m.put("662", "Розрахунки з депонентами");
        m.put("67", "Розрахунки з учасниками");
        m.put("671", "Розрахунки за нарахованими дивідендами");
        m.put("672", "Розрахунки за іншими виплатами");
        m.put("68", "Розрахунки за іншими операціями");
        m.put("681", "Розрахунки за авансами одержаними");
        m.put("682", "Внутрішні розрахунки");
        m.put("683", "Внутрішньогосподарські розрахунки");
        m.put("684", "Розрахунки за нарахованими відсотками");
        m.put("685", "Розрахунки з іншими кредиторами");
        m.put("69", "Доходи майбутніх періодів");
        m.put("Клас", "7. Доходи і результати діяльності");
        m.put("70", "Доходи від реалізації");
        m.put("701", "Дохід від реалізації готової продукції");
        m.put("702", "Дохід від реалізації товарів");
        m.put("703", "Дохід від реалізації робіт і послуг");
        m.put("704", "Вирахування з доходу");
        m.put("705", "Перестрахування");
        m.put("71", "Інший операційний дохід");
        m.put("711", "Дохід від реалізації іноземної валюти");
        m.put("712", "Дохід від реалізації інших оборотних активів");
        m.put("713", "Дохід від операційної оренди активів");
        m.put("714", "Дохід від операційної курсової різниці");
        m.put("715", "Одержані штрафи, пені, неустойки");
        m.put("716", "Відшкодування раніше списаних активів");
        m.put("717", "Дохід від списання кредиторської заборгованості");
        m.put("718", "Дохід від безоплатно одержаних оборотних активів");
        m.put("719", "Інші доходи від операційної діяльності");
        m.put("72", "Дохід від участі в капіталі");
        m.put("721", "Дохід від інвестицій в асоційовані підприємства");
        m.put("722", "Дохід від спільної діяльності");
        m.put("723", "Дохід від інвестицій в дочірні підприємства");
        m.put("73", "Інші фінансові доходи");
        m.put("731", "Дивіденди одержані");
        m.put("732", "Відсотки одержані");
        m.put("733", "Інші доходи від фінансових операцій");
        m.put("74", "Інші доходи");
        m.put("741", "Дохід від реалізації фінансових інвестицій");
        m.put("742", "Дохід від реалізації необоротних активів");
        m.put("743", "Дохід від реалізації майнових комплексів");
        m.put("744", "Дохід від неопераційної курсової різниці");
        m.put("745", "Дохід від безоплатно одержаних активів");
        m.put("746", "Інші доходи від звичайної діяльності");
        m.put("75", "Надзвичайні доходи");
        m.put("751", "Відшкодування збитків від надзвичайних подій");
        m.put("752", "Інші надзвичайні доходи");
        m.put("76", "Страхові платежі");
        m.put("79", "Фінансові результати");
        m.put("791", "Результат операційної діяльності");
        m.put("792", "Результат фінансових операцій");
        m.put("793", "Результат іншої звичайної діяльності");
        m.put("794", "Результат надзвичайних подій");
        m.put("Клас", "8. Витрати за елементами");
        m.put("80", "Матеріальні витрати");
        m.put("801", "Витрати сировини й матеріалів");
        m.put("802", "Витрати купівельних напівфабрикатів та комплектуючих виробів");
        m.put("803", "Витрати палива й енергії");
        m.put("804", "Витрати тари й тарних матеріалів");
        m.put("805", "Витрати будівельних матеріалів");
        m.put("806", "Витрати запасних частин");
        m.put("807", "Витрати матеріалів сільськогосподарського призначення");
        m.put("808", "Витрати товарів");
        m.put("809", "Інші матеріальні витрати");
        m.put("81", "Витрати на оплату праці");
        m.put("811", "Виплати за окладами й тарифами");
        m.put("812", "Премії та заохочення");
        m.put("813", "Компенсаційні виплати");
        m.put("814", "Оплата відпусток");
        m.put("815", "Оплата іншого невідпрацьованого часу");
        m.put("816", "Інші витрати на оплату праці");
        m.put("82", "Відрахування на соціальні заходи");
        m.put("821", "Відрахування на пенсійне забезпечення");
        m.put("822", "Відрахування на соціальне страхування");
        m.put("823", "Страхування на випадок безробіття");
        m.put("824", "Відрахування на індивідуальне страхування");
        m.put("83", "Амортизація");
        m.put("831", "Амортизація основних засобів");
        m.put("832", "Амортизація інших необоротних матеріальних активів");
        m.put("833", "Амортизація нематеріальних активів");
        m.put("84", "Інші операційні витрати");
        m.put("85", "Інші затрати");
        m.put("Клас", "9. Витрати діяльності");
        m.put("90", "Собівартість реалізації");
        m.put("901", "Собівартість реалізованої готової продукції");
        m.put("902", "Собівартість реалізованих товарів");
        m.put("903", "Собівартість реалізованих робіт і послуг");
        m.put("904", "Страхові виплати");
        m.put("91", "Загальновиробничі витрати");
        m.put("92", "Адміністративні витрати");
        m.put("93", "Витрати на збут");
        m.put("94", "Інші витрати операційної діяльності");
        m.put("941", "Витрати на дослідження і розробки");
        m.put("942", "Собівартість реалізованої іноземної валюти");
        m.put("943", "Собівартість реалізованих виробничих запасів");
        m.put("944", "Сумнівні та безнадійні борги");
        m.put("945", "Втрати від операційної курсової різниці");
        m.put("946", "Втрати від знецінення запасів");
        m.put("947", "Нестачі і втрати від псування цінностей");
        m.put("948", "Визнані штрафи, пені, неустойки");
        m.put("949", "Інші витрати операційної діяльності");
        m.put("95", "Фінансові витрати");
        m.put("951", "Відсотки за кредит");
        m.put("952", "Інші фінансові витрати");
        m.put("96", "Втрати від участі в капіталі");
        m.put("961", "Втрати від інвестицій в асоційовані підприємства");
        m.put("962", "Втрати від спільної діяльності");
        m.put("963", "Втрати від інвестицій в дочірні підприємства");
        m.put("97", "Інші витрати");
        m.put("971", "Собівартість реалізованих фінансових інвестицій");
        m.put("972", "Собівартість реалізованих необоротних активів");
        m.put("973", "Собівартість реалізованих майнових комплексів");
        m.put("974", "Втрати від неопераційних курсових різниць");
        m.put("975", "Уцінка необоротних активів і фінансових інвестицій");
        m.put("976", "Списання необоротних активів");
        m.put("977", "Інші витрати звичайної діяльності");
        m.put("98", "Податок на прибуток");
        m.put("981", "Податок на прибуток від звичайної діяльності");
        m.put("982", "Податок на прибуток від надзвичайних подій");
        m.put("99", "Надзвичайні витрати");
        m.put("991", "Втрати від стихійного лиха");
        m.put("992", "Втрати від техногенних катастроф і аварій");
        m.put("993", "Інші надзвичайні витрати");
        m.put("01", "Орендовані необоротні активи");
        m.put("02", "Активи на відповідальному зберіганні");
        m.put("021", "Устаткування, прийняте для монтажу");
        m.put("022", "Матеріали, прийняті для переробки");
        m.put("023", "Матеріальні цінності на відповідальному зберіганні");
        m.put("024", "Товари, прийняті на комісію");
        m.put("025", "Майно в довірчому управлінні");
        m.put("03", "Контрактні зобов'язання");
        m.put("04", "Непередбачені активи й зобов'язання");
        m.put("041", "Непередбачені активи");
        m.put("042", "Непередбачені зобов'язання");
        m.put("05", "Гарантії та забезпечення надані");
        m.put("06", "Гарантії та забезпечення отримані");
        m.put("07", "Списані активи");
        m.put("071", "Списана дебіторська заборгованість");
        m.put("072", "Невідшкодовані нестачі і втрати від псування цінностей");
        m.put("08", "Бланки суворого обліку");

    }

    private void endValue(){

        if(code1.equals("231") || code1.equals("233") || code1.equals("234") || code1.equals("230")){
            endCred = new SimpleDoubleProperty(strCred.get()+cred.get());
            endDeb = new SimpleDoubleProperty(strDeb.get()+deb.get());
        }
        else {
            double buff = strDeb.get() - strCred.get() + deb.get() - cred.get();
            if(buff > 0){
                  endDeb  = new SimpleDoubleProperty(buff);
                 endCred = new SimpleDoubleProperty(0);
            }else if (buff < 0){
                buff = (buff * (-1));
                endCred = new SimpleDoubleProperty(buff);
                endDeb =  new SimpleDoubleProperty(0);
            }else {
                endCred = new SimpleDoubleProperty(0);
                endDeb =  new SimpleDoubleProperty(0);
            }
        }

    }

    public String getCode() {
        return code.get();

    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public String getName() {
        if(m.containsKey(getCode())){
            return m.get(getCode());
        }else return " ";
    }


    public String getStrDeb1() {
        return String.format(Locale.US,"₴ %,.2f", strDeb.get());
    }

    public double getStrDeb() {
        return strDeb.get();
    }

    public void setStrDeb(double strDeb) {
        this.strDeb.set(strDeb);
    }

    public double getStrCred() {
        return strCred.get();
    }

    public String getStrCred1() {
        return String.format(Locale.US,"₴ %,.2f", strCred.get());
    }

    public void setStrCred(double strCred) {
        this.strCred.set(strCred);
    }

    public double getDeb() {
        return deb.get();
    }

    public String getDeb1() {
        return String.format(Locale.US,"₴ %,.2f", deb.get());
    }

    public void setDeb(double deb) {
        this.deb.set(deb);
    }

    public double getCred() {
        return cred.get();
    }

    public String getCred1() {
        return String.format(Locale.US,"₴ %,.2f", cred.get());
    }

    public void setCred(double cred) {
        this.cred.set(cred);
    }

    public double getEndDeb() {
        return endDeb.get();
    }

    public String getEndDeb1() {
        return String.format(Locale.US,"₴ %,.2f", endDeb.get());
    }

    public double getEndCred() {
        return endCred.get();
    }

    public String getEndCred1() {
        return String.format(Locale.US,"₴ %,.2f", endCred.get());
    }
    public void AddOld_Debet(double d){
        this.deb = new SimpleDoubleProperty((getDeb()+d));
    }

}
