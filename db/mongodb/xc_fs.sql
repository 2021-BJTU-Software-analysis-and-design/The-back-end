/*
 Navicat MongoDB Data Transfer

 Source Server         : xuecheng-cms
 Source Server Type    : MongoDB
 Source Server Version : 30425
 Source Host           : localhost:27017
 Source Schema         : xc_fs

 Target Server Type    : MongoDB
 Target Server Version : 30425
 File Encoding         : 65001

 Date: 03/08/2020 19:25:59
*/


// ----------------------------
// Collection structure for filesystem
// ----------------------------
db.getCollection("filesystem").drop();
db.createCollection("filesystem");

// ----------------------------
// Documents of filesystem
// ----------------------------
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6QeriAKFOtAACAXgYRrqs696.png",
    filePath: "group1/M00/00/00/CgEBmF6QeriAKFOtAACAXgYRrqs696.png",
    fileSize: NumberLong("32862"),
    fileName: "22742482.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "fileTagTest",
    metadata: {
        name: "test"
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6RnLWABBpDAAFd38IDSEw686.png",
    filePath: "group1/M00/00/00/CgEBmF6RnLWABBpDAAFd38IDSEw686.png",
    fileSize: NumberLong("89567"),
    fileName: "校徽144x144.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "123",
    filetag: "123",
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6RnUaACZuqAAFd38IDSEw446.png",
    filePath: "group1/M00/00/00/CgEBmF6RnUaACZuqAAFd38IDSEw446.png",
    fileSize: NumberLong("89567"),
    fileName: "校徽144x144.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "123",
    filetag: "123",
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6RrXmAb_ZnAACAXgYRrqs447.png",
    filePath: "group1/M00/00/00/CgEBmF6RrXmAb_ZnAACAXgYRrqs447.png",
    fileSize: NumberLong("32862"),
    fileName: "22742482.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6Rr26AKlT5AACAXgYRrqs238.png",
    filePath: "group1/M00/00/00/CgEBmF6Rr26AKlT5AACAXgYRrqs238.png",
    fileSize: NumberLong("32862"),
    fileName: "22742482.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6RsmiAAvp5AAD0Gha1eeI828.png",
    filePath: "group1/M00/00/00/CgEBmF6RsmiAAvp5AAD0Gha1eeI828.png",
    fileSize: NumberLong("62490"),
    fileName: "就业小程序logo.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6Rsq2AMZUuAAD0Gha1eeI131.png",
    filePath: "group1/M00/00/00/CgEBmF6Rsq2AMZUuAAD0Gha1eeI131.png",
    fileSize: NumberLong("62490"),
    fileName: "就业小程序logo.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6RvDqAfUgWAACAXgYRrqs470.png",
    filePath: "group1/M00/00/00/CgEBmF6RvDqAfUgWAACAXgYRrqs470.png",
    fileSize: NumberLong("32862"),
    fileName: "22742482.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6RwoWAa3G9AAD0Gha1eeI261.png",
    filePath: "group1/M00/00/00/CgEBmF6RwoWAa3G9AAD0Gha1eeI261.png",
    fileSize: NumberLong("62490"),
    fileName: "就业小程序logo.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6RwwGAQS-bAACAXgYRrqs533.png",
    filePath: "group1/M00/00/00/CgEBmF6RwwGAQS-bAACAXgYRrqs533.png",
    fileSize: NumberLong("32862"),
    fileName: "22742482.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6Rz1uABkK-AAD0Gha1eeI642.png",
    filePath: "group1/M00/00/00/CgEBmF6Rz1uABkK-AAD0Gha1eeI642.png",
    fileSize: NumberLong("62490"),
    fileName: "就业小程序logo.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6R0XuASNZ0AACAXgYRrqs106.png",
    filePath: "group1/M00/00/00/CgEBmF6R0XuASNZ0AACAXgYRrqs106.png",
    fileSize: NumberLong("32862"),
    fileName: "22742482.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6R452AP5SfAAD0Gha1eeI157.png",
    filePath: "group1/M00/00/00/CgEBmF6R452AP5SfAAD0Gha1eeI157.png",
    fileSize: NumberLong("62490"),
    fileName: "就业小程序logo.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6R46yASwO0AAD0Gha1eeI310.png",
    filePath: "group1/M00/00/00/CgEBmF6R46yASwO0AAD0Gha1eeI310.png",
    fileSize: NumberLong("62490"),
    fileName: "就业小程序logo.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6R5ASARXjIAAD0Gha1eeI451.png",
    filePath: "group1/M00/00/00/CgEBmF6R5ASARXjIAAD0Gha1eeI451.png",
    fileSize: NumberLong("62490"),
    fileName: "就业小程序logo.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6R5AiAVmD-AAD0Gha1eeI833.png",
    filePath: "group1/M00/00/00/CgEBmF6R5AiAVmD-AAD0Gha1eeI833.png",
    fileSize: NumberLong("62490"),
    fileName: "就业小程序logo.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6R5EaALot3AAD0Gha1eeI403.png",
    filePath: "group1/M00/00/00/CgEBmF6R5EaALot3AAD0Gha1eeI403.png",
    fileSize: NumberLong("62490"),
    fileName: "就业小程序logo.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6R65OAavWnAACAXgYRrqs059.png",
    filePath: "group1/M00/00/00/CgEBmF6R65OAavWnAACAXgYRrqs059.png",
    fileSize: NumberLong("32862"),
    fileName: "22742482.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6R66GADrC2AACAXgYRrqs209.png",
    filePath: "group1/M00/00/00/CgEBmF6R66GADrC2AACAXgYRrqs209.png",
    fileSize: NumberLong("32862"),
    fileName: "22742482.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6UZd-AVh_fAADLxuFd6fM615.png",
    filePath: "group1/M00/00/00/CgEBmF6UZd-AVh_fAADLxuFd6fM615.png",
    fileSize: NumberLong("52166"),
    fileName: "pythonLogo.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6UZymAY11wAADLxuFd6fM540.png",
    filePath: "group1/M00/00/00/CgEBmF6UZymAY11wAADLxuFd6fM540.png",
    fileSize: NumberLong("52166"),
    fileName: "pythonLogo.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6XrTCAF3dGAADLxuFd6fM914.png",
    filePath: "group1/M00/00/00/CgEBmF6XrTCAF3dGAADLxuFd6fM914.png",
    fileSize: NumberLong("52166"),
    fileName: "pythonLogo.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6XsgmAdCcSAAAiiFmYMc884.jfif",
    filePath: "group1/M00/00/00/CgEBmF6XsgmAdCcSAAAiiFmYMc884.jfif",
    fileSize: NumberLong("8840"),
    fileName: "springboot.jfif",
    fileType: "image/jpeg",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6iPG6AB3fxAAAVbi_bt0c874.png",
    filePath: "group1/M00/00/00/CgEBmF6iPG6AB3fxAAAVbi_bt0c874.png",
    fileSize: NumberLong("5486"),
    fileName: "makedown.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF6z8PeAAVvcAAGXY7UHwck569.png",
    filePath: "group1/M00/00/00/CgEBmF6z8PeAAVvcAAGXY7UHwck569.png",
    fileSize: NumberLong("104291"),
    fileName: "MGMWERX_Coding-Sprint-Facebook-Event-Cover.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF60zv-AEocQAADLxuFd6fM859.png",
    filePath: "group1/M00/00/00/CgEBmF60zv-AEocQAADLxuFd6fM859.png",
    fileSize: NumberLong("52166"),
    fileName: "pythonLogo.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF62TgOAb6ffAAKTSLygbdM889.jpg",
    filePath: "group1/M00/00/00/CgEBmF62TgOAb6ffAAKTSLygbdM889.jpg",
    fileSize: NumberLong("168776"),
    fileName: "TIM图片20200502150109.jpg",
    fileType: "image/jpeg",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF791hGADi8bAADLxuFd6fM537.png",
    filePath: "group1/M00/00/00/CgEBmF791hGADi8bAADLxuFd6fM537.png",
    fileSize: NumberLong("52166"),
    fileName: "pythonLogo.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF791n2AbIu9AADLxuFd6fM736.png",
    filePath: "group1/M00/00/00/CgEBmF791n2AbIu9AADLxuFd6fM736.png",
    fileSize: NumberLong("52166"),
    fileName: "pythonLogo.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF791oOAOPSLAADLxuFd6fM220.png",
    filePath: "group1/M00/00/00/CgEBmF791oOAOPSLAADLxuFd6fM220.png",
    fileSize: NumberLong("52166"),
    fileName: "pythonLogo.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF791oiAYEz3AAAVbi_bt0c865.png",
    filePath: "group1/M00/00/00/CgEBmF791oiAYEz3AAAVbi_bt0c865.png",
    fileSize: NumberLong("5486"),
    fileName: "makedown.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/CgEBmF791rqADSlbAADLxuFd6fM831.png",
    filePath: "group1/M00/00/00/CgEBmF791rqADSlbAADLxuFd6fM831.png",
    fileSize: NumberLong("52166"),
    fileName: "pythonLogo.png",
    fileType: "image/png",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    businesskey: "businessKeyTest",
    filetag: "course",
    metadata: {
        test: NumberInt("666")
    },
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
