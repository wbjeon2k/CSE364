package kr.twww.mrs.data.object;

public class User
{
    public enum Gender
    {
        MALE,
        FEMALE,
        UNKNOWN
    }

    public enum Age
    {
        UNDER_18,
        BETWEEN_18_24,
        BETWEEN_25_34,
        BETWEEN_35_44,
        BETWEEN_45_49,
        BETWEEN_50_55,
        OVER_55,
        UNKNOWN
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
        WRITER,
        UNKNOWN
    }

    public int userId;
    public Gender gender;
    public Age age;
    public Occupation occupation;
    public String zipCode;

    public static Gender ConvertGender( char _gender )
    {
        //M,F 을 Enum 으로 변경
        if(_gender == 'M'){
            return Gender.MALE;
        }
        else if(_gender == 'F'){
            return Gender.FEMALE;
        }
        else{
            System.out.println("WRONG DATA IN GENDER!");
            return null;
        }
        // TODO: 주어진 성별 텍스트를 enum Gender로 반환
    }

    public static Gender ConvertGender( String _gender )
    {
        // TODO

        return null;
    }

    public static Age ConvertAge( int _age )
    {
        //나이 범위 나누기
        if(_age < 18){
            return Age.UNDER_18;
        }
        else if(_age >= 18 && _age < 25 ){
            return Age.BETWEEN_18_24;
        }
        else if(_age >= 25 && _age < 35){
            return Age.BETWEEN_25_34;
        }
        else if(_age >= 35 && _age < 45){
            return Age.BETWEEN_35_44;
        }
        else if(_age >= 45 && _age < 50){
            return Age.BETWEEN_45_49;
        }
        else if(_age >= 50 && _age < 56){
            return Age.BETWEEN_50_55;
        }
        else if(_age >= 56){
            return Age.OVER_55;
        }
        else{
            System.out.println("WRONG DATA IN AGE!");
            return null;
        }

    }

    public static Age ConvertAge( String _age )
    {
        // TODO

        return null;
    }

    public static Occupation ConvertOccupationByIndex( int _occupation )
    {
        return Occupation.values()[_occupation];

    }

    public static Occupation ConvertOccupationByText( String _occupation )
    {
        // TODO

        return null;
    }
}
