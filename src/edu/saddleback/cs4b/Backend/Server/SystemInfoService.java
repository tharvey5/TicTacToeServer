package edu.saddleback.cs4b.Backend.Server;

public class SystemInfoService {
    private static volatile SystemInfoService si = null;

    private SystemInfoService() {}

    public static SystemInfoService getInstance() {
        if (si == null) {
            synchronized (SystemInfoService.class) {
                if (si == null) {
                    si = new SystemInfoService();
                }
            }
        }
        return si;
    }
}
