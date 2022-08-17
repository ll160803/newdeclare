package cc.mrbird.febs.zq.entity;

import lombok.Data;

import java.util.List;
@Data
public class UserInfo {
    private List<ZqBCopySciencepublish> publishList;
    private List<ZqBCopySciencesearch> sciencesearchList;
    private List<ZqBCopyScientificprize> scientificprizeList;
    private List<ZqBCopyPublicarticle> publicarticleList;
    private String summary;
    private String goal;
    private String employ;
}
