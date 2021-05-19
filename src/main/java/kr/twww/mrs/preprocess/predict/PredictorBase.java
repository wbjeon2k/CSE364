package kr.twww.mrs.preprocess.predict;

public abstract class PredictorBase
{
    public abstract void Setup();

    public abstract String GetChecksum() throws Exception;
    public abstract String GetSavedChecksum() throws Exception;
    public abstract void SaveChecksum( String checksum ) throws Exception;
}
