package cc.mrbird.febs.kh.entity;


import lombok.Data;

import java.util.List;

@Data
public class UserInfo {
    private List<KhBCopySciencepublish> publishList;
    private List<KhBCopySciencesearch> sciencesearchList;
    private List<KhBCopyScientificprize> scientificprizeList;
    private List<KhBCopyPublicarticle> publicarticleList;
    private String summary;
}
