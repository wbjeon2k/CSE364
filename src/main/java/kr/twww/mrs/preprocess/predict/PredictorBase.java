package kr.twww.mrs.preprocess.predict;

public abstract class PredictorBase
{
    public abstract void Setup();

    public abstract String GetChecksum();
    public abstract void SaveChecksum( String checksum );
}