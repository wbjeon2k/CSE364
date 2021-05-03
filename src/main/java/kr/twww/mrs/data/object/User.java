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
        switch ( _gender )
        {
            case 'M':
                return Gender.MALE;

            case 'F':
                return Gender.FEMALE;
        }

        System.out.println("Error: Invalid gender character");
        return null;
    }

    public static Gender ConvertGender( String _gender )
    {
        if ( _gender == null )
        {
            System.out.println("Error: Invalid gender string");
            return null;
        }

        if ( _gender.isEmpty() )
        {
            return Gender.UNKNOWN;
        }

        if ( _gender.length() != 1 )
        {
            System.out.println("Error: Invalid gender string");
            return null;
        }

        return ConvertGender(_gender.charAt(0));
    }

    public static Age ConvertAge( int _age )
    {
        if ( _age < 18 )
        {
            return Age.UNDER_18;
        }
        else if ( _age <= 24 )
        {
            return Age.BETWEEN_18_24;
        }
        else if ( _age <= 34 )
        {
            return Age.BETWEEN_25_34;
        }
        else if ( _age <= 44 )
        {
            return Age.BETWEEN_35_44;
        }
        else if ( _age <= 49 )
        {
            return Age.BETWEEN_45_49;
        }
        else if ( _age <= 55 )
        {
            return Age.BETWEEN_50_55;
        }
        else
        {
            return Age.OVER_55;
        }
    }

    public static Age ConvertAge( String _age )
    {
        if ( _age == null )
        {
            System.out.println("Error: Invalid age string");
            return null;
        }

        if ( _age.isEmpty() )
        {
            return Age.UNKNOWN;
        }

        try
        {
            return ConvertAge(Integer.parseInt(_age));
        }
        catch ( Exception e )
        {
            System.out.println("Error: Invalid age string");
        }

        return null;
    }

    public static Occupation ConvertOccupationByIndex( int _occupation )
    {
        return Occupation.values()[_occupation];
    }

    public static Occupation ConvertOccupationByText( String _occupation )
    {
        if ( _occupation == null )
        {
            System.out.println("Error: Invalid occupation string");
            return null;
        }

        if ( _occupation.isEmpty() )
        {
            return Occupation.UNKNOWN;
        }

        _occupation = _occupation.replaceAll("[^a-zA-Z0-9]", "");
        _occupation = _occupation.toUpperCase();

        for ( var i : Occupation.values() )
        {
            if ( i == Occupation.UNKNOWN )
            {
                continue;
            }

            var splitOccupation = i.name().split("_OR_");

            for ( var j : splitOccupation )
            {
                var occupation = j;
                occupation = occupation.replaceAll("[^a-zA-Z0-9]", "");
                occupation = occupation.toUpperCase();

                if ( occupation.equals("COLLEGE") )
                {
                    occupation += "STUDENT";
                }

                if ( _occupation.equals(occupation) )
                {
                    return i;
                }
            }
        }

        System.out.println("Error: Invalid occupation string");
        return null;
    }
}
