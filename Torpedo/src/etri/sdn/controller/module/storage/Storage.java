package etri.sdn.controller.module.storage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Method;

import etri.sdn.controller.OFModel;
import etri.sdn.controller.util.Logger;

public class Storage extends OFModel {

	private final String TYPE_ERROR_MESSAGE = "Wrong Input Type Error!";
	private final String INSERTING_ERROR_MESSAGE = "Inserting Error!";
	private final String DELETING_ERROR_MESSAGE = "Deleting Error!";
	private final String RETRIEVING_ALL_ERROR_MESSAGE = "Retrieving All Error!";
	private final String RETRIEVING_ERROR_MESSAGE = "Retrieving Error!";
	private final String ENSURE_INDEX_ERROR_MESSAGE = "Ensuring Index Error!";
	private final String DROP_INDEX_ERROR_MESSAGE = "Drop Index Error!";
	private final String OBJECT_MAPPING_ERROR_MESSAGE = "Object Mapping Error!";
	private final String GET_INDEX_ERROR_MESSAGE = "Get Index Error!";

	private OFMStorageManager storageInstance;
	private ObjectMapper om;

	Storage(OFMStorageManager storageManager) {
		this.storageInstance = storageManager;
		om =  new ObjectMapper();
	}

	private RESTApi[] apis = {
			new RESTApi(
					"/wm/core/controller/database",
					new Restlet() {
						@Override
						public void handle(Request request, Response response) {
							if(request.getMethod() == Method.GET) {
								List<String> listDBs = null;
								String r = null;
								try {
									listDBs = storageInstance.retrieveDBs();//
									Logger.stderr(listDBs.toString());
								} catch (StorageException e) {
									e.printStackTrace();
								}

								ObjectMapper om = new ObjectMapper();

								try {
									r = om.writeValueAsString(listDBs);
									response.setEntity(r, MediaType.APPLICATION_JSON);
								} catch (Exception e) {
									Logger.stderr(OBJECT_MAPPING_ERROR_MESSAGE + e);
									return;
								}
								response.setEntity(r, MediaType.APPLICATION_JSON);
							}
						}
					}
					),
					new RESTApi(
							"/wm/core/controller/database/drop/{dbname}",
							new Restlet() {
								@Override
								public void handle(Request request, Response response) {
									if(request.getMethod() == Method.GET) {
										String dbName = (String) request.getAttributes().get("dbname");
										String r = null;
										try {
											storageInstance.dropDB(dbName); //
											Logger.stderr(dbName + " db dropped.");
										} catch (StorageException e) {
											e.printStackTrace();
										}
										response.setEntity(r, MediaType.APPLICATION_JSON);
									}
								}
							}),
							new RESTApi(
									"/wm/core/controller/database/{dbname}/{collection}/insert",
									new Restlet() {
										@Override
										public void handle(Request request, Response response) {

											String dbName = (String) request.getAttributes().get("dbname");
											String collection = (String) request.getAttributes().get("collection");

											if(request.getMethod() == Method.POST) {
												try {
													String query = request.getEntityAsText();
													System.out.println(query);
													TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>(){}; 
													Map<String, Object> map = null;
													try {
														map = om.readValue(query, typeRef);
													} catch (Exception e) {
														Logger.stderr(OBJECT_MAPPING_ERROR_MESSAGE + e);
														return;
													} 
													
													ObjectMapper om = new ObjectMapper();
													String r = null;
													try {
														r = om.writeValueAsString(map);
													} catch (IOException e) {
														e.printStackTrace();
													}
													storageInstance.insert(dbName, collection, (Map<String, Object>)map);
													response.setEntity(r, MediaType.APPLICATION_JSON);
												} catch (StorageException e) {
													Logger.stderr(INSERTING_ERROR_MESSAGE + e);
												}
											} 
										}
									}
									),
									new RESTApi(
											"/wm/core/controller/database/{dbname}/{collection}/upsert",
											new Restlet() {
												@Override
												public void handle(Request request, Response response) {


													String dbName = (String) request.getAttributes().get("dbname");
													String collection = (String) request.getAttributes().get("collection");

													if(request.getMethod() == Method.POST) {
														try {
															String text = request.getEntityAsText();
															System.out.println(text);
															TypeReference<List<Map<String, Object>>> typeRef = new TypeReference<List<Map<String, Object>>>(){}; 
															List<Map<String, Object>> list = null;
															Map<String, Object> key = null;
															Map<String, Object> query = null;

															try {
																list = om.readValue(text, typeRef);
																if(list.size() != 2) {
																	Logger.stderr(TYPE_ERROR_MESSAGE);
																	return;
																}
																key = list.get(0);
																query = list.get(1);
															} catch (Exception e) {
																Logger.stderr(OBJECT_MAPPING_ERROR_MESSAGE + e);
																return;
															} 

															storageInstance.upsert(dbName, collection, key, query);
															response.setEntity(query.toString(), MediaType.APPLICATION_JSON);
														} catch (StorageException e) {
															e.printStackTrace();
														}
													} 
												}
											}
											),		
											new RESTApi(
													"/wm/core/controller/database/{dbname}/{collection}/update",
													new Restlet() {
														@Override
														public void handle(Request request, Response response) {


															String dbName = (String) request.getAttributes().get("dbname");
															String collection = (String) request.getAttributes().get("collection");

															if(request.getMethod() == Method.POST) {
																try {
																	String text = request.getEntityAsText();
																	System.out.println(text);
																	TypeReference<List<Map<String, Object>>> typeRef = new TypeReference<List<Map<String, Object>>>(){}; 
																	List<Map<String, Object>> list = null;
																	Map<String, Object> key = null;
																	Map<String, Object> query = null;

																	try {
																		list = om.readValue(text, typeRef);
																		if(list.size() != 2) {
																			Logger.stderr(TYPE_ERROR_MESSAGE);
																			return;
																		}
																		key = list.get(0);
																		query = list.get(1);
																	} catch (Exception e) {
																		Logger.stderr(OBJECT_MAPPING_ERROR_MESSAGE + e);
																		return;
																	} 

																	storageInstance.update(dbName, collection, key, query);
																	response.setEntity(query.toString(), MediaType.APPLICATION_JSON);
																} catch (StorageException e) {
																	e.printStackTrace();
																}
															} 
														}
													}
													),		
													new RESTApi(
															"/wm/core/controller/database/{dbname}/{collection}/delete",
															new Restlet() {
																@Override
																public void handle(Request request, Response response) {
																	String dbName = (String) request.getAttributes().get("dbname");
																	String collection = (String) request.getAttributes().get("collection");

																	if(request.getMethod() == Method.POST) {
																		try {
																			String query = request.getEntityAsText();
																			System.out.println(query);
																			TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>(){}; 
																			Map<String, Object> map = null;
																			try {
																				map = om.readValue(query, typeRef);
																			} catch (Exception e) {
																				Logger.stderr(OBJECT_MAPPING_ERROR_MESSAGE + e);
																				return;
																			} 

																			storageInstance.delete(dbName, collection, (Map<String, Object>)map);
																			response.setEntity(map.toString(), MediaType.APPLICATION_JSON);
																		} catch (StorageException e) {
																			Logger.stderr(DELETING_ERROR_MESSAGE + e);;
																		}
																	}
																}
															}
															),
															new RESTApi(
																	"/wm/core/controller/database/{dbname}/{collection}/retrieve/all",
																	new Restlet() {
																		@Override
																		public void handle(Request request, Response response) {


																			String dbName = (String) request.getAttributes().get("dbname");
																			String collection = (String) request.getAttributes().get("collection");

																			if(request.getMethod() == Method.POST) {
																				try {
																					List<Map<String, Object>> list = storageInstance.retrieveAll(dbName, collection);
																					response.setEntity(list.toString(), MediaType.APPLICATION_JSON);
																				} catch (StorageException e) {
																					Logger.stderr(RETRIEVING_ALL_ERROR_MESSAGE + e);
																				}
																			} 
																		}
																	}
																	),
																	new RESTApi(
																			"/wm/core/controller/database/{dbname}/{collection}/retrieve",
																			new Restlet() {
																				@Override
																				public void handle(Request request, Response response) {

																					String dbName = (String) request.getAttributes().get("dbname");
																					String collection = (String) request.getAttributes().get("collection");

																					if(request.getMethod() == Method.POST) {
																						try {
																							String query = request.getEntityAsText();
																							System.out.println(query);
																							TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>(){}; 
																							Map<String, Object> map = null;
																							try {
																								map = om.readValue(query, typeRef);
																							} catch (Exception e) {
																								Logger.stderr(OBJECT_MAPPING_ERROR_MESSAGE + e);
																								return;
																							} 

																							List<Map<String, Object>> list = storageInstance.retrieve(dbName, collection, (Map<String, Object>)map);
																							response.setEntity(list.toString(), MediaType.APPLICATION_JSON);
																						} catch (StorageException e) {
																							Logger.stderr(RETRIEVING_ERROR_MESSAGE + e);
																							//e.printStackTrace();
																						}
																					} 
																				}
																			}
																			),
																			new RESTApi(
																					"/wm/core/controller/database/{dbname}/{collection}/ensureindex",
																					new Restlet() {
																						@Override
																						public void handle(Request request, Response response) {

																							String dbName = (String) request.getAttributes().get("dbname");
																							String collection = (String) request.getAttributes().get("collection");

																							if(request.getMethod() == Method.POST) {
																								try {
																									String query = request.getEntityAsText();
																									System.out.println(query);
																									TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>(){}; 
																									Map<String, Object> map = null;
																									try {
																										map = om.readValue(query, typeRef);
																									} catch (Exception e) {
																										Logger.stderr(OBJECT_MAPPING_ERROR_MESSAGE + e);
																										return;
																									} 
																									storageInstance.ensureIndex(dbName, collection, map);
																									response.setEntity(map.toString(), MediaType.APPLICATION_JSON);
																								} catch (StorageException e) {
																									Logger.stderr(ENSURE_INDEX_ERROR_MESSAGE + e);
																								}
																							} 																							
																						}
																					}
																					),
																					new RESTApi(
																							"/wm/core/controller/database/{dbname}/{collection}/dropindex",
																							new Restlet() {
																								@Override
																								public void handle(Request request, Response response) {

																									String dbName = (String) request.getAttributes().get("dbname");
																									String collection = (String) request.getAttributes().get("collection");

																									if(request.getMethod() == Method.POST) {
																										try {
																											String query = request.getEntityAsText();
																											System.out.println(query);
																											TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>(){}; 
																											Map<String, Object> map = null;
																											try {
																												map = om.readValue(query, typeRef);
																											} catch (Exception e) {
																												Logger.stderr(OBJECT_MAPPING_ERROR_MESSAGE + e);
																												return;
																											} 
																											storageInstance.dropIndex(dbName, collection, map);
																											response.setEntity(map.toString(), MediaType.APPLICATION_JSON);
																										} catch (StorageException e) {
																											Logger.stderr(DROP_INDEX_ERROR_MESSAGE + e);
																										}
																									} 																										
																								}
																							}
																							),
																							new RESTApi(
																									"/wm/core/controller/database/{dbname}/{collection}/getindex",
																									new Restlet() {
																										@Override
																										public void handle(Request request, Response response) {

																											String dbName = (String) request.getAttributes().get("dbname");
																											String collection = (String) request.getAttributes().get("collection");

																											if(request.getMethod() == Method.GET) {
																												try {
																													List<Map<String, Object>> list = storageInstance.getIndex(dbName, collection);
																													ObjectMapper om = new ObjectMapper();
																													String r = null;
																													try {
																														r = om.writeValueAsString(list);
																													} catch (IOException e) {
																														e.printStackTrace();
																													}
																													response.setEntity(r, MediaType.APPLICATION_JSON);
																												} catch (StorageException e) {
																													Logger.stderr(GET_INDEX_ERROR_MESSAGE + e);
																												}
																											} 
																										}
																									}
																									)};
	@Override
	public RESTApi[] getAllRestApi() {
		return apis;
	}

}