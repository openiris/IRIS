package etri.sdn.controller.module.staticentrymanager;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Status;

import java.io.IOException;
import java.io.StringWriter;
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

        if (! (dpid.toLowerCase().equals("all") || manager.isSwitchExists(dpid))) {
            response.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return;
            // FIXME: give reason why.
        }

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
        String dpid = (String) request.getAttributes().get("dpid");

        if (!manager.isSwitchExists(dpid)) {
            response.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return;
            // FIXME: give reason why.
        }

        String jsonString = request.getEntityAsText();
        StringWriter sWriter = new StringWriter();
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator jsonGenerator;
        String status;

        Map<String, Object> entry;
        Object flowName;

        try {
            entry = StaticFlowEntry.jsonToStaticFlowEntry(jsonString);
            if (entry == null) {
                throw new Exception("The input is null");
            }

            flowName = entry.get("name");
            if (flowName != null) {
                StaticFlowEntry.checkInputField(entry.keySet());
                StaticFlowEntry.checkMatchPrerequisite(entry);
                manager.addFlow((String) flowName, entry, dpid);
                status = "Entry pushed: " + flowName;
            } else {
                status = "The name field is indispensable";
            }
            response.setStatus(Status.SUCCESS_CREATED);
        } catch (UnsupportedOperationException e) {
            response.setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            status = "Fail to push entry: Wrong version for the switch";
        } catch (StaticFlowEntryException e) {
            response.setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            status = e.getReason();
        } catch (IOException e) {
            response.setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            status = "Fail to parse JSON format";
            e.printStackTrace();
        } catch (Exception e) {
            response.setStatus(Status.SERVER_ERROR_INTERNAL);
            e.printStackTrace();
            status = "Fail to insert entry";
        }

        try {
            jsonGenerator = jsonFactory.createJsonGenerator(sWriter);
            jsonGenerator.writeStartObject();
            jsonGenerator.writeFieldName("result");
            jsonGenerator.writeString(status);
            jsonGenerator.writeEndObject();
            jsonGenerator.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String r = sWriter.toString();
        response.setEntity(r, MediaType.APPLICATION_JSON);
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
