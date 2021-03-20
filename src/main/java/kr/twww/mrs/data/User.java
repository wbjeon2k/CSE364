package kr.twww.mrs.data;

public class User
{
    public enum Gender
    {
        MALE,
        FEMALE
    }

    public enum Age
    {
        UNDER_18,
        BETWEEN_18_24,
        BETWEEN_25_34,
        BETWEEN_35_44,
        BETWEEN_45_49,
        BETWEEN_50_55,
        OVER_55
    }

    public enum Occupation
    {
        OTHER,
	    ACADEMIC_OR_EDUCATOR,
        ARTIST,
        CLERICAL_OR_ADMIN,
        COLLEGE_OR_GRAD_STUDENT,
        CUSTOMER_SERVICE,
        DOCTOR_OR_HEALTH_CARE,
        EXECUTIVE_OR_MANAGERIAL,
        FARMER,
        HOMEMAKER,
        K_12_STUDENT,
        LAWYER,
        PROGRAMMER,
        RETIRED,
        SALES_OR_MARKETING,
        SCIENTIST,
        SELF_EMPLOYED,
        TECHNICIAN_OR_ENGINEER,
        TRADESMAN_OR_CRAFTSMAN,
        UNEMPLOYED,
        WRITER
    }

    public int userId;
    public Gender gender;
    public Age age;
    public Occupation occupation;
    public int zipCode;

    public static Gender ConvertGender( char _gender )
    {
        // TODO
        return null;
    }

    public static Age ConvertAge( int _age )
    {
        // TODO
        return null;
    }

    public static Occupation ConvertOccupation( int _occupation )
    {
        // TODO
        return null;
    }
}
