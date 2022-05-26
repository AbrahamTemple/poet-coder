package com.abraham.coder.pkg.utils;

import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description
 * @author AbrahamVong
 * @since 26/5/2022
 */
public class PackageUtils {


    private static final String root = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java";

    /**
     * 获取目录下所有java类的包
     * @return
     */
    @SneakyThrows
    public static List<String> getPathClazzPkg(){

        List<String> pkgList = new ArrayList<>();

        Files.walk(Paths.get(root)).filter(Files::isRegularFile).forEach(path ->{
            if(path.toString().substring(path.toString().lastIndexOf(".")+1).equals("java")){
                pkgList.add(path.toString().substring(0,path.toString().lastIndexOf(".")).replace(root + File.separator,"").replace(File.separator,"."));
            }
        });

        return pkgList;
    }

    /**
     * 获取目录下所有java类的父包
     * @return
     */
    @SneakyThrows
    public static Set<String> getPathPkg(){
        Set<String> pkgSet = new HashSet<>();

        Files.walk(Paths.get(root)).filter(Files::isRegularFile).forEach(path ->{
            if(path.toString().substring(path.toString().lastIndexOf(".")+1).equals("java")){
                pkgSet.add(path.toString().substring(0,path.toString().lastIndexOf("\\")).replace(root + File.separator,"").replace(File.separator,"."));
            }
        });

        return pkgSet;
    }

    /**
     * 获取目录的包
     * @param dir 目录名
     * @return
     */
    @SneakyThrows
    public static String getOnePathPkg(String dir){

        AtomicReference<String> pkg = new AtomicReference<>("/");

        Files.walk(Paths.get(root)).filter(Files::isRegularFile).forEach(path ->{
            String var1 = path.toString().substring(0, path.toString().lastIndexOf("\\")).replace(root + File.separator, "").replace(File.separator, ".");
            if(path.toString().substring(path.toString().lastIndexOf(".")+1).equals("java") && var1.contains(dir)){
                pkg.set(var1.substring(0,var1.lastIndexOf(".")));
            }
        });

        return pkg.get();
    }


    /**
     * 构建文件包路径
     * @param dir 父目录名称
     * @param type 父目录下的新目录
     * @param name 文件名称
     * @return
     */
    @SneakyThrows
    public static String buildPkg(String dir, String type, String name){
        AtomicReference<String> pkg = new AtomicReference<>("");

        Files.walk(Paths.get(root)).filter(Files::isDirectory).forEach(path ->{
            String dirs = path.toString().replace(root + File.separator, "").replace(File.separator, ".");
            if(dirs.contains(dir)) {
                pkg.set(dirs.substring(0,dirs.lastIndexOf(dir)-1));
            }
        });

        return pkg.get().concat("."+ dir + "." + type + "." + name);
    }

}
