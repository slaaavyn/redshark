package tk.slaaavyn.redshark.dto.categories;

import tk.slaaavyn.redshark.model.File;

import java.util.ArrayList;
import java.util.List;

public class DirectoryDto extends FileDto {

    private List<FileDto> children;

    protected DirectoryDto() {}

    protected DirectoryDto(File file, List<File> children) {
        super(file);

        List<FileDto> childrenDto = new ArrayList<>();
        children.forEach(child -> {
            childrenDto.add(new FileDto(child));
        });

        this.children = childrenDto;
    }

    public List<FileDto> getChildren() {
        return children;
    }

    public void setChildren(List<FileDto> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "DirectoryDto{" +
                "children=" + children +
                "} " + super.toString();
    }
}
