package casino.common;

public enum Type {
    ODD,
    EVEN;

    public static Type getName(String name){
        for (Type type : values()){
            if(type.toString().equals(name)){
                return type;
            }
        }
        return null;
    }
}
