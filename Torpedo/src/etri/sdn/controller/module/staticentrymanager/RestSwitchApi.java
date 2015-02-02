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

        if (method == Method.GET) {
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
     * Make json result for response.
     *
     * @param response {@link Response} to write status code.
     * @param status   {@link Status} Status code.
     * @param message  Message for this response.
     */
    private void makeResult(Response response, Status status, String message) {
        StringWriter sWriter = new StringWriter();
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator jsonGenerator;

        try {
            jsonGenerator = jsonFactory.createJsonGenerator(sWriter);
            jsonGenerator.writeStartObject();
            jsonGenerator.writeFieldName("result");
            jsonGenerator.writeString(message);
            jsonGenerator.writeEndObject();
            jsonGenerator.close();

            String r = sWriter.toString();
            response.setEntity(r, MediaType.APPLICATION_JSON);
            response.setStatus(status);
        } catch (Exception e) {
            e.printStackTrace();
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

        if (!(dpid.toLowerCase().equals("all") || manager.isSwitchExists(dpid))) {
            makeResult(response,
                    Status.CLIENT_ERROR_NOT_FOUND,
                    "That switch is not exists.");
            return;
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
            makeResult(response,
                    Status.CLIENT_ERROR_NOT_FOUND,
                    "That switch is not exists.");
            return;
        }

        String jsonString = request.getEntityAsText();

        Map<String, Object> entry;
        Object flowName;

        try {
            entry = StaticFlowEntry.jsonToStaticFlowEntry(jsonString);
            if (entry == null) {
                makeResult(response, Status.CLIENT_ERROR_BAD_REQUEST, "The input was null");
                return;
            }

            flowName = entry.get("name");
            if (flowName != null) {
                StaticFlowEntry.checkInputField(entry.keySet());
                StaticFlowEntry.checkMatchPrerequisite(entry);
                manager.addFlow((String) flowName, entry, dpid);

                String message = "Entry pushed: " + flowName;
                makeResult(response, Status.SUCCESS_CREATED, message);
            } else {
                makeResult(response,
                        Status.CLIENT_ERROR_BAD_REQUEST,
                        "The name field is indispensable");
            }
            response.setStatus(Status.SUCCESS_CREATED);
        } catch (UnsupportedOperationException e) {
            makeResult(response,
                    Status.CLIENT_ERROR_BAD_REQUEST,
                    "Fail to push entry: Wrong version for the switch");
        } catch (StaticFlowEntryException e) {
            makeResult(response,
                    Status.CLIENT_ERROR_BAD_REQUEST,
                    e.getReason());
        } catch (IOException e) {
            makeResult(response,
                    Status.CLIENT_ERROR_BAD_REQUEST,
                    "Fail to parse JSON format");
            e.printStackTrace();
        } catch (Exception e) {
            makeResult(response,
                    Status.SERVER_ERROR_INTERNAL,
                    "Fail to insert entry");
            e.printStackTrace();
        }


    }

    /**
     * Reload entries.
     *
     * @param request
     * @param response
     */
    private void handlePut(Request request, Response response) {
        String dpid = (String) request.getAttributes().get("dpid");

        if (!(dpid.toLowerCase().equals("all") || manager.isSwitchExists(dpid))) {
            response.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return;
            // FIXME: give reason why.
        }

        try {
            if (dpid.toLowerCase().equals("all")) {
                if (!manager.getStaticFlowEntryStorage().getFlowModNameToDpidIndex().isEmpty()) {
                    manager.reloadAllFlowsToSwitch();
                    makeResult(response,
                            Status.SUCCESS_OK,
                            "All entries are reloaded to switches.");
                } else {
                    makeResult(response,
                            Status.CLIENT_ERROR_NOT_FOUND,
                            "There is no entry");
                }
            } else {
                if (!manager.getStaticFlowEntryStorage().getDpidToFlowModNameIndex().isEmpty()) {
                    manager.reloadFlowsToSwitch(dpid);
                    makeResult(response,
                            Status.SUCCESS_OK,
                            "Entries are reloaded to switch: " + dpid + ".");
                } else {
                    makeResult(response,
                            Status.CLIENT_ERROR_NOT_FOUND,
                            "There is no entry");
                }
            }
        } catch (UnsupportedOperationException e) {
            makeResult(response,
                    Status.CLIENT_ERROR_BAD_REQUEST,
                    "Fail to reload entry: Wrong version for the switch");
        } catch (Exception e) {
            e.printStackTrace();
            makeResult(response,
                    Status.SERVER_ERROR_INTERNAL,
                    "Fail to reload entries to switches.");
        }
    }

    /**
     * Clear switch.
     *
     * @param request
     * @param response
     */
    private void handleDelete(Request request, Response response) {
        String dpid = (String) request.getAttributes().get("dpid");

        if (!(dpid.toLowerCase().equals("all") || manager.isSwitchExists(dpid))) {
            response.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return;
            // FIXME: give reason why.
        }

        Set<String> flows;
        if (dpid.toLowerCase().equals("all")) {
            flows = manager.getStaticFlowEntryStorage().getFlowModMap().keySet();
            if (flows.isEmpty()) {
                flows = null;
            }
        } else {
            flows = manager.getStaticFlowEntryStorage().getDpidToFlowModNameIndex().get(dpid);
        }

        if (flows != null) {
            boolean ret = true;
            StringBuilder exceptionlist = new StringBuilder();

            //Avoiding ConcurrentModificationException
            Set<String> flowsToDel = new HashSet<String>();
            flowsToDel.addAll(flows);

            for (String flow : flowsToDel) {
                try {
                    manager.deleteFlow(flow);
                } catch (UnsupportedOperationException e) {
                    ret = false;
                    exceptionlist.append("Wrong version for the switch. ");
                } catch (StaticFlowEntryException e) {
                    ret = false;
                    exceptionlist.append(e.getReason());
                    exceptionlist.append(". ");
                } catch (Exception e) {
                    ret = false;
                    e.printStackTrace();
                }
            }

            if (ret) {
                makeResult(response, Status.SUCCESS_OK, "All entries are cleared.");
            } else {
                makeResult(response,
                        Status.SERVER_ERROR_INTERNAL,
                        "Failure clearing entries: " + exceptionlist);
            }
        } else {
            makeResult(response, Status.CLIENT_ERROR_BAD_REQUEST, "There is no entry.");
        }

    }
}
