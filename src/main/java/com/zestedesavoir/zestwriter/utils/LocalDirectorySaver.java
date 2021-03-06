/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zestedesavoir.zestwriter.utils;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author fdambrine
 */
public class LocalDirectorySaver implements StorageSaver{
    private String baseDirectory;
    private File baseDirectoryDescriptor;

    public LocalDirectorySaver(String baseDirectory)throws RuntimeException{
        this.baseDirectory = baseDirectory;
        openDirCreateIfNecessary();
    }

    @Override
    public String getBaseDirectory() {
        return this.baseDirectory;
    }

    public void setBaseDirectory(String baseDirectory){
        this.baseDirectory = baseDirectory;

    }
    private void openDirCreateIfNecessary(){
        this.baseDirectoryDescriptor = new File(baseDirectory);
        if(!this.baseDirectoryDescriptor.exists() && !this.baseDirectoryDescriptor.mkdirs()){
            throw new RuntimeException("Could not create " + baseDirectory);
        }
    }

    @Override
    public boolean isStorageCurrentlyWritable() {
        return this.baseDirectoryDescriptor.canWrite();
    }

    @Override
    public boolean isStorageCurrentlyReadable() {
        return this.baseDirectoryDescriptor.canRead();
    }

    @Override
    public void saveDirectory(String subdirectory) throws SecurityException {
        File subdir = new File(getBaseDirectory() + File.separator + subdirectory);
        subdir.mkdir();
    }

    @Override
    public void saveFile(String fpath, String content) throws SecurityException,IOException {
        File subfile = new File(getBaseDirectory() + File.separator + fpath);
        if(subfile.createNewFile()){
            try(java.io.FileWriter writer = new java.io.FileWriter(subfile)){
                writer.write(content);
            }
        }
    }

}
