package com.hzgc.ftpserver.local;

import org.apache.ftpserver.ftplet.FileSystemFactory;
import org.apache.ftpserver.ftplet.FileSystemView;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.User;


public class LocalFileSystemFactory implements FileSystemFactory{
    public FileSystemView createFileSystemView(User user) throws FtpException {
        return null;
    }
}