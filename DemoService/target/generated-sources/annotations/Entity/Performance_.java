package Entity;

import Entity.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-12-12T23:31:45")
@StaticMetamodel(Performance.class)
public class Performance_ { 

    public static volatile SingularAttribute<Performance, User> uid;
    public static volatile SingularAttribute<Performance, Boolean> isAlarm;
    public static volatile SingularAttribute<Performance, String> usage;
    public static volatile SingularAttribute<Performance, Date> eventTime;
    public static volatile SingularAttribute<Performance, Integer> id;
    public static volatile SingularAttribute<Performance, String> type;

}