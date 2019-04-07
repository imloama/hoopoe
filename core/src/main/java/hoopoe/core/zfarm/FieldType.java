package hoopoe.core.zfarm;

public enum FieldType {

    // 无类型
    None("None"),
    String("String"),
    Email("Email"),
    Password("Password"),
    Telphone("Telphone"),
    IP("IP"),
    URL("URL"),
    Image("Image"),
    File("File"),
    Number("Number"),
    Integer("Integer"),
    Float("Float"),
    Date("Date"),
    DateTime("DateTime"),
    Time("Time"),
    Ref("Ref"),
    Select("Select")
    ;

    private String value;
    private FieldType(String value){
        this.value = value;
    }

}
