package com.zju.webapp.controller;

import com.zju.model.Bag;
import com.zju.model.Constants;
import com.zju.model.Index;
import com.zju.model.Item;
import com.zju.model.Library;
import com.zju.model.Result;
import com.zju.model.Rule;
import com.zju.model.User;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.zju.service.BagManager;
import com.zju.service.IndexManager;
import com.zju.service.ItemManager;
import com.zju.service.ResultManager;
import com.zju.service.RuleManager;
import com.zju.service.UserManager;
import com.zju.webapp.pojo.FileUpload;
import com.zju.webapp.util.UserUtil;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controller class to upload Files.
 * <p/>
 * <p>
 * <a href="FileUploadFormController.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/fileupload*")
public class FileUploadController extends BaseFormController {

	private RuleManager ruleManager = null;
	private ItemManager itemManager = null;
	private IndexManager indexManager = null;
	private ResultManager resultManager = null;
	private BagManager bagManager = null;
	private UserManager userManager = null;
	
    public FileUploadController() {
        setCancelView("redirect:/mainMenu");
        setSuccessView("uploadDisplay");
    }

    @ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    public FileUpload showForm() {
        return new FileUpload();
    }
    
    private String getItemId(String content,HttpServletRequest request, Set<Item> items, Set<String> reasoning) {

		String[] item1 = content.split(",");
		String[] item2 = item1[0].split("c==");
		
		if (item2.length != 1) {
			if (item2[0].indexOf("SI") == -1 || item2[0].indexOf("SI") != -1 && item2[1].replace('\'', ' ').trim().length() < 5) {
				String indexId = item2[1].replace('\'', ' ').trim();
				String value = item1[1].substring(1);
				String unit = null;
				String[] units = content.split("u==");
				if (units.length != 1) {
					int num1 = units[1].indexOf(',');
					int num2 = units[1].indexOf(')');
					if (num1 != -1) {
						unit = units[1].substring(1,num1-1);
					} else if(num2 != -1) {
						unit = units[1].substring(1,num2-1);
					}
				}
				if (value.startsWith("==")) {
					value = value.substring(1);
				} else if (value.startsWith("v==")) {
					value = value.substring(4);
					value = value.substring(0, value.length() - 1);
				}
				if (value.charAt(value.length()-1) == ')') {
					value = value.substring(0, value.length());
				}
				if(indexId.length() != 4) {
					int k = 4-indexId.length();
					for (int i=0;i<k;i++)
						indexId = "0" + indexId;
				}
				//System.out.println("::"+indexId);
				if (indexId.length() != 4) return null;
				Index index = indexManager.getIndex(indexId);
				Item newItem = itemManager.exsitItem(index.getId(), value);
				if (newItem == null) {
					Item item = new Item();
					item.setIndex(index);
					item.setValue(value);
					item.setUnit(unit);
					item.setCreateUser(UserUtil.getCurrentUser(request, userManager));
					item.setCreateTime(new Date());
					newItem = itemManager.addItem(item);
				}
				items.add(newItem);
				return "I" + newItem.getId();
			} else {
				String indexId = item2[1].replace('\'', ' ').trim();
				//疾病
				if (indexId.length() == 5) {
					String result = item1[1].split("\'")[1];
					System.out.println(result);
					List<Result> rlist = resultManager.getResults(result);
					Result res = null;
					if (rlist != null && rlist.size() != 0) {
						for(Result r : rlist) {
							if (r.getContent().equals(result)) {
								res = r;
							}
						}
					}
					if (res == null) {
						res = new Result();
						res.setContent(result);
						res = resultManager.addResult(res);	
					}
					reasoning.add(res.getId().toString());
					
					return "R" + res.getId();
				}
			}

		} else {
			//病人信息
			String info = "";
			if (content.indexOf("s==") != -1) {
				info += content.split("s==")[1].substring(1, 2);
			}
			if (content.indexOf("l==") != -1) {
				if (!StringUtils.isEmpty(info)) info += ",";
				info += content.split("l==")[1].substring(1, 2);
			}
			
			Library lib = itemManager.getInfo(info);
			if (lib != null) {
				return "P" + lib.getId();
			}
		}

    	return null;
    }

    private void importRule(InputStream stream, HttpServletRequest request, Bag bag) {
    	 BufferedReader buf = new BufferedReader(new InputStreamReader(stream));
    	 String str = null;
    	 try {
             do {
            	 str = buf.readLine();
            	 if (str != null) {
            		 if (str.startsWith("rule")) {
            			 Rule rule = new Rule();
            			 rule.addBag(bag);
            			 Set<Item> items = new HashSet<Item>();
            			 StringBuilder builder = new StringBuilder();
            			 JSONObject root = new JSONObject();
            			 String ruleName = str.substring(5, str.length()-1);
            			 System.out.println(ruleName);
            			 
            			 buf.readLine();
            			 str = buf.readLine();
            			 while (!str.equals("then")) {
            				 builder.append(str.trim());
            				 str = buf.readLine();
            			 }
            			 String ruleStr = builder.toString(); 
            			 Set<String> reasoning = new HashSet<String>();
            			 dealItems(request, root, ruleStr, items, reasoning);
            			 
            			 System.out.println(root.toString());
            			 
            			 rule.setRelation(root.toString());
            			 rule.setActivate(false);
            			 rule.setItems(items);
            			 rule.setName(ruleName);
            			 String re = null;
            			 for (String r : reasoning) {
            				 if (re == null) {
            					 re = r;
            				 } else {
            					 re += "," + r;
            				 }
            			 }
            			 rule.setReReasoning(re);
            			 User user = UserUtil.getCurrentUser(request, userManager);
            			 Date now = new Date();
            			 rule.setCreateUser(user);
            			 rule.setModifyUser(user);
            			 rule.setCreateTime(now);
            			 rule.setModifyTime(now);
            			 
            			 while (!str.equals("end")) {
            				 str = str.trim();
            				 if (str.startsWith("r.setResultSet")) {
            					 str = str.substring(16, str.length()-3);
            					 List<Result> list = resultManager.getResults(str);
            					 if (list == null || list.size() == 0) {
            						 Result r = new Result();
            						 r.setContent(str);
            						 rule.addResult(resultManager.addResult(r));
            					 } else {
            						 rule.addResult(list.get(0));
            					 }
            				 }
            				 str = buf.readLine();
            			 }
            			 
            			 List<Rule> eRule = ruleManager.searchRule(rule.getName());
            			 if (eRule == null || eRule.size() == 0) {
            				 ruleManager.addRule(rule);
            			 } else {
            				 Rule oldRule = eRule.get(0);
            				 boolean flag = false;
            				 for (Bag b : oldRule.getBags()) {
            					 if (b.getName().equals(bag.getName())) {
            						 flag = true;
            					 }
            				 }
            				 if (!flag) {
            					 oldRule.addBag(bag);
            					 ruleManager.save(oldRule);
            				 }
            			 }
             		 }
            	 }
             } while (str != null);
         } catch (Exception e) {
             e.printStackTrace();
         }
    }

	private void dealItems(HttpServletRequest request, JSONObject root, String ruleStr, Set<Item> items, Set<String> reasoning)
			throws JSONException {
		String[] andSp = ruleStr.split("and");
		 System.out.println(andSp.length);
		 if (andSp.length != 1) {
			 root.put("id", "and");
			 JSONArray array = new JSONArray();
			 
			 for (int i=0; i< andSp.length; i++) {
				 JSONObject obj = new JSONObject();
				 String[] orSp = andSp[i].split("or");
				 // =1 则没有第二层的或者关系
				 if (orSp.length == 1) {
					 String itemId = getItemId(orSp[0],request,items, reasoning);
					 if(itemId != null) {
						 obj.put("id", itemId);
					 }
				 } else {
					 obj.put("id", "or");
					 JSONArray array2 = new JSONArray();
					 for (int j=0; j<orSp.length; j++) {
						 JSONObject obj2 = new JSONObject();
						 String itemId = getItemId(orSp[j],request,items, reasoning);
						 if(itemId != null) {
							 obj2.put("id", itemId);
						 }
						 array2.put(obj2);
					 }
					 obj.put("children", array2);
				 }
				 array.put(obj);
			 }
			 root.put("children", array);
		 } else {
			 String[] orSp = ruleStr.split("or");
			 if (orSp.length != 1) {
				 root.put("id", "or");
				 JSONArray array = new JSONArray();
				 for (int j=0; j<orSp.length; j++) {
					 JSONObject obj = new JSONObject();
					 String itemId = getItemId(orSp[j],request,items, reasoning);
					 if(itemId != null) {
						 obj.put("id", itemId);
					 }
					 array.put(obj);
				 }
				 root.put("children", array);
			 } else {
				 String itemId = getItemId(orSp[0],request,items, reasoning);
				 if(itemId != null) {
					 root.put("id", itemId);
				 }
			 }
		 }
	}
 
    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(FileUpload fileUpload, BindingResult errors, HttpServletRequest request)
            throws Exception {

        if (request.getParameter("cancel") != null) {
            return getCancelView();
        }

        if (validator != null) { // validator is null during testing
            validator.validate(fileUpload, errors);

            if (errors.hasErrors()) {
                return "fileupload";
            }
        }

        // validate a file was entered
        if (fileUpload.getFile().length == 0) {
            Object[] args =
                    new Object[]{getText("uploadForm.file", request.getLocale())};
            errors.rejectValue("file", "errors.required", args, "File");

            return "fileupload";
        }

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");

        // the directory to upload to
        String uploadDir = getServletContext().getRealPath("/resources") + "/" + request.getRemoteUser() + "/";

        // Create the directory if it doesn't exist
        File dirPath = new File(uploadDir);

        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

        //retrieve the file data
        InputStream stream = file.getInputStream();
        
        String bagName = fileUpload.getName();
        Bag b;
        
        List<Bag> bags = bagManager.getBagByName(bagName);
        if (bags == null || bags.size() == 0) {
        	Bag bag = new Bag();
        	bag.setParenetID(new Long(0));
        	bag.setCore(false);
        	bag.setName(bagName);
        	b = bagManager.save(bag);
        } else {
        	b = bags.get(0);
        }
        
        System.out.println(bagName);
        importRule(stream, request, b);
        //write the file to the file specified
        OutputStream bos = new FileOutputStream(uploadDir + file.getOriginalFilename());
      /*  int bytesRead;
        byte[] buffer = new byte[8192];*/

       /* while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }*/

        bos.close();

        //close the stream
        stream.close();

        // place the data into the request for retrieval on next page
        request.setAttribute("friendlyName", fileUpload.getName());
        request.setAttribute("fileName", file.getOriginalFilename());
        request.setAttribute("contentType", file.getContentType());
        request.setAttribute("size", file.getSize() + " bytes");
        request.setAttribute("location", dirPath.getAbsolutePath() + Constants.FILE_SEP + file.getOriginalFilename());

        String link = request.getContextPath() + "/resources" + "/" + request.getRemoteUser() + "/";
        request.setAttribute("link", link + file.getOriginalFilename());

        return getSuccessView();
    }
    
    @Autowired
    public void setRuleManager(RuleManager ruleManager) {
		this.ruleManager = ruleManager;
	}
    @Autowired
	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
    @Autowired
	public void setResultManager(ResultManager resultManager) {
		this.resultManager = resultManager;
	}
    @Autowired
	public void setBagManager(BagManager bagManager) {
		this.bagManager = bagManager;
	}
    @Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
    @Autowired
	public void setIndexManager(IndexManager indexManager) {
		this.indexManager = indexManager;
	}
}
