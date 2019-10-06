package ga.strikepractice.web.data;

import lombok.Getter;

public enum DataColumn {

    KILLS("kills"),
    DEATHS("deaths"),
    LMS("lms"),
    PARTY_VS_PARTY_WINS("party_vs_party_wins"),
    BRACKET("brackets"),
    GLOBAL_ELO("global_elo"),
    ;

    @Getter
    private String columnName;

    DataColumn(String columnName) {
        this.columnName = columnName;
    }
}
