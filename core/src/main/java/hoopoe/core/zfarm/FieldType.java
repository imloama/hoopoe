package hoopoe.core.zfarm;

public enum FieldType {

    // 无类型
    None("none"),
    String("String"),
    Email("Email"),
    Password("password"),
    Telphone("Telphone"),
    IP("IP"),
    URL("URL"),
    Image("image"),
    File("file"),
    Number("Number"),
    Integer("Integer"),
    Float("Float"),
    Date("date"),
    DateTime("datetime"),
    Time("time"),
    Ref("ref"),
    Select("select")
    ;

    private String value;
    private FieldType(String value){
        this.value = value;
    }

}
