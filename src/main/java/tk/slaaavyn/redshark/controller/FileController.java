package tk.slaaavyn.redshark.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tk.slaaavyn.redshark.config.EndpointConstants;
import tk.slaaavyn.redshark.dto.categories.FileDto;
import tk.slaaavyn.redshark.dto.categories.FileDtoFactory;
import tk.slaaavyn.redshark.model.File;
import tk.slaaavyn.redshark.model.FileType;
import tk.slaaavyn.redshark.security.jwt.JwtUser;
import tk.slaaavyn.redshark.service.FileService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = EndpointConstants.FILES_ENDPOINT)
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/create-file")
    @ApiOperation(value = "Create file in user device. Action as role: ADMIN, USER")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<FileDto> createFile(
            @RequestParam(name = "deviceId") Long deviceId,
            @RequestParam(name = "parentId", required = false) Long parentId,
            @RequestParam(name = "fileName") String fileName) {
        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        File file = fileService.createFile(deviceId, jwtUser.getId(), parentId, fileName, FileType.FILE);
        if (file == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(FileDtoFactory.toDto(file));
    }

    @PostMapping("/create-directory")
    @ApiOperation(value = "Create directory in user device. Action as role: ADMIN, USER")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<FileDto> createDirectory(
            @RequestParam(name = "deviceId") Long deviceId,
            @RequestParam(name = "parentId", required = false) Long parentId,
            @RequestParam(name = "fileName") String fileName) {
        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        File file = fileService.createFile(deviceId, jwtUser.getId(), parentId, fileName, FileType.DIRECTORY);
        if (file == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(FileDtoFactory.toDto(file));
    }

    @GetMapping("/find")
    @ApiOperation(value = "Find files by name in user device. Action as role: ADMIN, USER")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<List<FileDto>> findFiles(
            @RequestParam(name = "deviceId") Long deviceId,
            @RequestParam(name = "fileName") String fileName) {
        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        List<File> files = fileService.findFilesInDevice(deviceId, fileName, jwtUser.getId());
        List<FileDto> fileDtoList = new ArrayList<>();

        files.forEach(file -> {
            fileDtoList.add(FileDtoFactory.toDto(file));
        });

        return ResponseEntity.ok(fileDtoList);
    }

    @GetMapping
    @ApiOperation(value = "Get device file tree by id. " +
            "If you do not specify fileId, a list of root directory files is displayed. " +
            "Action as role: ADMIN, USER")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<FileDto> getFiles(
            @RequestParam(name = "deviceId", required = true) Long deviceId,
            @RequestParam(name = "fileId", required = false) Long fileId) {

        if (deviceId == null) {
            return ResponseEntity.badRequest().build();
        }

        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        // Get ROOT("/") folder on device
        if(fileId == null || fileId == 0) {
            List<File> files = fileService.getRootFiles(deviceId, jwtUser.getId());

            if(files == null) {
                return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.ok(FileDtoFactory.toRootDirectoryDto(deviceId, files));
        }

        // Get another directory by id
        File file = fileService.getFile(fileId, deviceId, jwtUser.getId());
        List<File> directoryChildren = fileService.getDirectoryChildren(file);
        if(file == null) {
            return ResponseEntity.badRequest().build();
        }

        if (file.getFileType() == FileType.DIRECTORY && directoryChildren != null) {
            return ResponseEntity.ok(FileDtoFactory.toDto(file, directoryChildren));
        }

        return ResponseEntity.ok(FileDtoFactory.toDto(file));
    }

    @PutMapping("/move")
    @ApiOperation(value = "Moving a file(or directory) to another directory. Action as role: ADMIN, USER")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<FileDto> moveFile(
            @RequestParam(name = "deviceId") Long deviceId,
            @RequestParam(name = "fileId") Long fileId,
            @RequestParam(name = "destinationFileId") Long destinationFileId) {
        if(fileId.equals(destinationFileId)) {
            return ResponseEntity.badRequest().build();
        }

        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        File file = fileService.moveFile(deviceId, fileId, destinationFileId, jwtUser.getId());
        if (file == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(FileDtoFactory.toDto(file));
    }
}
