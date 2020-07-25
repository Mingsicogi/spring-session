package mins.study.session;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;

@Slf4j
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode
public class SessionAdditionalInfo implements Serializable {
    private static final long serialVersionUID = -4142386168596393316L;

    private String ip;
    private String device;
    private String agent;
    private String os;

    public SessionAdditionalInfo(HttpServletRequest request) {
        this.ip = request.getRemoteAddr();

        String header = request.getHeader("User-Agent");
        try {
            Parser parser = new Parser();
            Client userAgent = parser.parse(header);

            this.agent = userAgent.userAgent.family;
            this.device = userAgent.device.family;
            this.os = userAgent.os.family;
        } catch (IOException e) {
            log.error("UAParser is not working... Device info will be set by default...");
            this.device = "unknown";
            this.agent = "unknown";
            this.os = "unknown";
        }
    }
}
