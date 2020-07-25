package mins.study.session;

import lombok.Getter;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;

@Getter
public class SessionAdditionalInfo {
    private final String ip;
    private final String device;
    private final String agent;
    private final String os;

    public SessionAdditionalInfo(HttpServletRequest request) throws Exception{
        this.ip = request.getRemoteAddr();

        String header = request.getHeader("User-Agent");
        Parser parser = new Parser();
        Client userAgent = parser.parse(header);

        this.agent = userAgent.userAgent.family;
        this.device = userAgent.device.family;
        this.os = userAgent.os.family;
    }
}
