package etri.sdn.controller.module.staticentrymanager;

import org.codehaus.jackson.map.ObjectMapper;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Status;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author kjwon15
 * @created 15. 1. 29
 */
public class RestSwitchApi extends Restlet {

    private final OFMStaticFlowEntryManager manager;

    public RestSwitchApi(OFMStaticFlowEntryManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(Request request, Response response) {
        Method method = request.getMethod();

        if(method == Method.GET) {
            handleGet(request, response);
        } else if (method == Method.POST) {
            handlePost(request, response);
        } else if (method == Method.PUT) {
            handlePut(request, response);
        } else if (method == Method.DELETE) {
            handleDelete(request, response);
        } else {
            response.setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
            return;
        }
    }

    /**
     * List entries.
     *
     * @param request
     * @param response
     */
    private void handleGet(Request request, Response response) {
        String dpid = (String) request.getAttributes().get("dpid");
        Set<String> flows = new HashSet<String>();

        ObjectMapper om = new ObjectMapper();
        try {
            StaticFlowEntryStorage storage = manager.getStaticFlowEntryStorage();
            Map<String, Map<String, Object>> entries = storage.getFlowModMap(dpid);
            String res = om.writeValueAsString(entries);
            response.setEntity(res, MediaType.APPLICATION_JSON);
            return;
        } catch (IOException e) {
            response.setStatus(Status.SERVER_ERROR_INTERNAL);
            e.printStackTrace();
        }
    }

    /**
     * Add entry.
     *
     * @param request
     * @param response
     */
    private void handlePost(Request request, Response response) {

    }

    /**
     * Reload entries.
     * @param request
     * @param response
     */
    private void handlePut(Request request, Response response) {

    }

    /**
     * Clear switch.
     * @param request
     * @param response
     */
    private void handleDelete(Request request, Response response) {

    }
}
