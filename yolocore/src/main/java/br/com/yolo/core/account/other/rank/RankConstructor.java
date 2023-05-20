package br.com.yolo.core.account.other.rank;

import br.com.yolo.core.account.other.rank.type.Group;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RankConstructor {

    private Group group;
    private String author;

    private long attributedIn, expireIn;

    public boolean isExpired() {
        return expireIn < attributedIn;
    }
}
