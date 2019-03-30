package hoopoe.core.zfarm;

public enum FieldType {

    // 无类型
    None("none"),
    String("String"),
    Email("Email"),
    Telphone("Telphone"),
    IP("IP"),
    URL("URL"),
    Number("Number"),
    Integer("Integer"),
    Float("Float"),
    Date("date"),
    DateTime("datetime"),
    Time("time"),
    Ref("ref")
    ;

    private String value;
    private FieldType(String value){
        this.value = value;
    }

}
