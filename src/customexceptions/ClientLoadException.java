package customexceptions;

public class ClientLoadException extends Exception
{
    public ClientLoadException(String err)
    {
        super(err);
    }
}
