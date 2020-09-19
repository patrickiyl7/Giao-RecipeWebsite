package edu.northeastern.cs5500.recipe.view;

import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

import edu.northeastern.cs5500.recipe.JsonTransformer;
import edu.northeastern.cs5500.recipe.controller.IngredientController;
import edu.northeastern.cs5500.recipe.controller.RecipeController;
import edu.northeastern.cs5500.recipe.controller.ReviewCommentController;
import edu.northeastern.cs5500.recipe.controller.ReviewController;
import edu.northeastern.cs5500.recipe.controller.StepController;
import edu.northeastern.cs5500.recipe.controller.TagController;
import edu.northeastern.cs5500.recipe.controller.UserController;
import edu.northeastern.cs5500.recipe.model.Ingredient;
import edu.northeastern.cs5500.recipe.model.Recipe;
import edu.northeastern.cs5500.recipe.model.Review;
import edu.northeastern.cs5500.recipe.model.Step;
import edu.northeastern.cs5500.recipe.model.Tag;
import edu.northeastern.cs5500.recipe.model.User;
import edu.northeastern.cs5500.recipe.tools.Tools;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

@Singleton
@Slf4j
public class RecipeView implements View {

    private Integer pageNumIndex;
    private Integer pageNumHome;
    private Integer pageSize = 5;
    private static final String USER_SESSION_ID = "user";

    @Inject
    RecipeView() {}

    @Inject JsonTransformer jsonTransformer;

    @Inject RecipeController recipeController;

    @Inject IngredientController ingredientController;

    @Inject StepController stepController;

    @Inject ReviewCommentController reviewCommentController;

    @Inject UserController userController;

    @Inject ReviewController reviewController;

    @Inject TagController tagController;

    @Override
    public void register() {
        log.info("RecipeView > register");
        staticFileLocation("/public");
        get(
                "/index",
                (request, response) -> {
                    pageNumIndex = 1;
                    Map<String, Object> model = new HashMap<String, Object>();
                    model.put("user", request.session().attribute(USER_SESSION_ID));
                    model.put("template", "templates/index.vtl");
                    Collection<Recipe> recipeList =
                            recipeController.getPageRecipes(pageSize, pageNumIndex);
                    model.put("recipes", recipeList);
                    model.put("start_page", true);
                    model.put("page_num", pageNumIndex);
                    Map<String, Integer> tags = tagController.getAllTagsAndCount();
                    model.put("tags", tags);
                    if (recipeList.size() < pageSize) {
                        model.put("last_page", "true");
                    }
                    return new ModelAndView(model, "templates/layout.vtl");
                },
                new VelocityTemplateEngine());

        get(
                "/index/:page",
                (request, response) -> {
                    pageNumIndex = Integer.valueOf(request.params(":page"));

                    Map<String, Object> model = new HashMap<String, Object>();
                    model.put("template", "templates/index.vtl");
                    if (pageNumIndex == 1) {
                        model.put("start_page", true);
                    }
                    Collection<Recipe> recipeList =
                            recipeController.getPageRecipes(pageSize, pageNumIndex);
                    if (recipeList.size() < pageSize) {
                        model.put("last_page", true);
                    }
                    if (recipeList.size() == 0 && pageNumIndex > 1) {
                        recipeList = recipeController.getPageRecipes(pageSize, pageNumIndex - 1);
                    }
                    Map<String, Integer> tags = tagController.getAllTagsAndCount();
                    model.put("tags", tags);
                    model.put("recipes", recipeList);
                    model.put("page_num", pageNumIndex);
                    model.put("user", request.session().attribute(USER_SESSION_ID));
                    return new ModelAndView(model, "templates/layout.vtl");
                },
                new VelocityTemplateEngine());

        get(
                "/search/:param",
                (request, response) -> {
                    final String param = request.params(":param");
                    Map<String, Object> model = new HashMap<String, Object>();
                    model.put("user", request.session().attribute(USER_SESSION_ID));
                    model.put("template", "templates/index.vtl");
                    Collection<Recipe> recipeList = recipeController.getRecipesByTitle(param);
                    model.put("recipes", recipeList);
                    model.put("start_page", true);
                    model.put("page_num", pageNumIndex);
                    Map<String, Integer> tags = tagController.getAllTagsAndCount();
                    model.put("tags", tags);
                    if (recipeList.size() < pageSize) {
                        model.put("last_page", "true");
                    }
                    return new ModelAndView(model, "templates/layout.vtl");
                },
                new VelocityTemplateEngine());

        get(
                "/recipe/:id",
                (request, response) -> {
                    final String paramId = request.params(":id");
                    Map<String, Object> model = new HashMap<String, Object>();
                    User user = request.session().attribute(USER_SESSION_ID);
                    model.put("user", user);
                    model.put("template", "templates/recipe.vtl");
                    Recipe recipe = recipeController.getRecipe(paramId);
                    List<Ingredient> ingredients = ingredientController.getIngredients(paramId);
                    List<Step> steps = stepController.getSteps(paramId);
                    if (recipe == null) {
                        halt(404);
                    }
                    List<Review> reviewList = reviewController.getAllReviewsByRecipeId(paramId);
                    List<Tag> tagList = tagController.getTagsByRecipeId(paramId);
                    Integer size = reviewList.size();
                    model.put("recipe", recipe);
                    model.put("ingredients", ingredients);
                    model.put("steps", steps);
                    model.put("reviews", reviewList);
                    model.put("tags", tagList);

                    Integer end = (Integer) model.get("end");
                    if (model.containsKey("end")) {
                        if (size == 1 && end == 0) {
                            model.replace("end", reviewList.size() - 1);
                        }
                        if (size == 0 && end == 0) {
                            model.put("end", reviewList.size() + 1);
                        }
                    } else {
                        if (size == 1) {
                            model.put("end", reviewList.size() - 1);
                        } else if (size == 0) {
                            model.put("end", -1);
                        } else {
                            model.put("end", reviewList.size() - 1);
                        }
                    }

                    return new ModelAndView(model, "templates/layout.vtl");
                },
                new VelocityTemplateEngine());

        get(
                "/recipe",
                (request, response) -> {
                    Map<String, Object> model = new HashMap<String, Object>();
                    if (request.session().attribute("error") != null) {
                        model.put("error", request.session().attribute("error").toString());
                        request.session().removeAttribute("error");
                    }
                    model.put("user", request.session().attribute(USER_SESSION_ID));
                    model.put("template", "templates/create.vtl");
                    return new ModelAndView(model, "templates/layout.vtl");
                },
                new VelocityTemplateEngine());
        post(
                "/recipe-review",
                (request, response) -> {
                    request.attribute(
                            "org.eclipse.jetty.multipartConfig",
                            new MultipartConfigElement("/tmp"));
                    Map<String, String[]> parameterMap = request.raw().getParameterMap();
                    // Date date = new Date();
                    // Date date = String.join(",", parameterMap.get("date"));
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    String curDate = dateFormat.format(date);
                    String recipeId = request.queryParams("recipeId"); // "YiwenZhang_slava_chiken";
                    String content = String.join(",", parameterMap.get("content"));
                    String userName = String.join(",", parameterMap.get("username"));
                    Integer rating = Integer.parseInt(String.join(",", parameterMap.get("rating")));
                    String reviewId = "";
                    Review review =
                            new Review(userName, curDate, content, recipeId, rating, reviewId);
                    try {
                        ReviewController.addReview(review);
                        response.redirect("/recipe/" + recipeId);
                    } catch (Exception e) {
                        request.session().attribute("error", e);
                        response.redirect("/index");
                        return "";
                    }
                    return "";
                });

        post(
                "/recipe",
                (request, response) -> {
                    request.attribute(
                            "org.eclipse.jetty.multipartConfig",
                            new MultipartConfigElement("/tmp"));
                    Part uploadedFile = null;
                    Boolean hasImage = true;
                    try {
                        uploadedFile = request.raw().getPart("final_image");
                    } catch (IOException | ServletException e) {
                        hasImage = false;
                    }
                    String imageStr = "data:image/png;base64, ";
                    if (hasImage) {
                        BufferedImage imBuff = ImageIO.read(uploadedFile.getInputStream());
                        imageStr = "data:image/png;base64, " + Tools.encodeToString(imBuff, "png");
                    }
                    BufferedImage imBuff = ImageIO.read(uploadedFile.getInputStream());
                    imageStr = "data:image/png;base64, " + Tools.encodeToString(imBuff, "png");
                    Map<String, String[]> parameterMap = request.raw().getParameterMap();
                    String date = String.join(",", parameterMap.get("date"));
                    String title = String.join(",", parameterMap.get("title"));
                    String description = String.join(",", parameterMap.get("description"));
                    User user = request.session().attribute("user");
                    Recipe recipe =
                            new Recipe(
                                    date,
                                    title,
                                    user.getUserId(),
                                    description,
                                    imageStr,
                                    user.getfinalImage());
                    String recipeId = recipe.getRecipeId();
                    try {
                        recipeController.addRecipe(recipe);
                    } catch (Exception e) {
                        request.session().attribute("error", e);
                        response.redirect("/recipe");
                        return "";
                    }
                    Integer ingredient_max = Integer.valueOf(request.queryParams("ingredient-max"));
                    for (int i = 1; i < ingredient_max + 1; i++) {
                        String ingredientName =
                                String.join(",", parameterMap.get("ingredient_name" + i));
                        String amount = String.join(",", parameterMap.get("quantity" + i));
                        String unit = String.join(",", parameterMap.get("unit" + i));
                        Ingredient ingredient =
                                new Ingredient(ingredientName, recipeId, amount, unit);
                        ingredientController.addIngredient(ingredient);
                    }
                    Integer step_max = Integer.valueOf(request.queryParams("step-max"));
                    for (int i = 1; i < step_max + 1; i++) {
                        String stepDescription = String.join(",", parameterMap.get("step" + i));
                        Part stepFile = null;
                        hasImage = true;
                        String stepImage = "data:image/png;base64, ";
                        try {
                            stepFile = request.raw().getPart("step_image" + i);
                        } catch (IOException | ServletException e) {
                            hasImage = false;
                        }
                        if (hasImage) {
                            BufferedImage imBuffStep = ImageIO.read(stepFile.getInputStream());
                            stepImage =
                                    "data:image/png;base64, "
                                            + Tools.encodeToString(imBuffStep, "png");
                        }
                        Step step = new Step(recipeId, i, stepDescription, stepImage);
                        stepController.addStep(step);
                    }
                    Integer tag_max = Integer.valueOf(request.queryParams("tag-max"));
                    for (int i = 1; i < tag_max + 1; i++) {
                        String tagDescription = String.join(",", parameterMap.get("tag" + i));
                        Tag tag = new Tag(tagDescription, recipe.getRecipeId());
                        tagController.addTag(tag);
                    }

                    response.redirect("/index");
                    return "";
                });

        get(
                "/myrecipe",
                (request, response) -> {
                    pageNumHome = 1;
                    Map<String, Object> model = new HashMap<String, Object>();
                    User user = request.session().attribute("user");
                    if (user == null) {
                        response.redirect("/index");
                    }
                    model.put("start_page", true);
                    Collection<Recipe> myRecipes =
                            recipeController.getRecipesPageByUserName(
                                    user.getUserId(), pageSize, pageNumHome);
                    if (myRecipes.size() < pageSize) {
                        model.put("last_page", true);
                    }
                    model.put("template", "templates/myrecipe.vtl");
                    model.put("recipes", myRecipes);
                    model.put("user", user);
                    Map<String, Integer> tags = tagController.getAllTagsAndCount();
                    model.put("tags", tags);
                    return new ModelAndView(model, "templates/layout.vtl");
                },
                new VelocityTemplateEngine());

        get(
                "/myrecipe/:page",
                (request, response) -> {
                    pageNumHome = Integer.valueOf(request.params(":page"));
                    Map<String, Object> model = new HashMap<String, Object>();
                    // TODO User login
                    User user = request.session().attribute(USER_SESSION_ID);
                    if (user == null) {
                        response.redirect("/index");
                    }
                    Collection<Recipe> myRecipes =
                            recipeController.getRecipesPageByUserName(
                                    user.getUserId(), pageSize, pageNumHome);
                    if (myRecipes.size() < pageSize) {
                        model.put("last_page", true);
                    }
                    if (pageNumHome == 1) {
                        model.put("start_page", true);
                    }
                    if (myRecipes.size() == 0 && pageNumHome > 1) {
                        myRecipes = recipeController.getPageRecipes(pageSize, pageNumHome - 1);
                    }
                    model.put("template", "templates/myrecipe.vtl");
                    model.put("recipes", myRecipes);
                    model.put("user", user);
                    Map<String, Integer> tags = tagController.getAllTagsAndCount();
                    model.put("tags", tags);
                    return new ModelAndView(model, "templates/layout.vtl");
                },
                new VelocityTemplateEngine());

        get(
                "/update/:id",
                (request, response) -> {
                    String paramId = request.params(":id");
                    Map<String, Object> model = new HashMap<String, Object>();
                    User user = request.session().attribute(USER_SESSION_ID);
                    model.put("user", user);
                    model.put("template", "templates/updaterecipe.vtl");
                    Recipe recipe = recipeController.getRecipe(paramId);
                    if (recipe == null) {
                        response.redirect("/index");
                    }
                    if (!user.getUserId().equals(recipe.getUserName())) {
                        response.redirect("/index");
                    }
                    System.out.println("hello");
                    List<Ingredient> ingredients = ingredientController.getIngredients(paramId);
                    List<Step> steps = stepController.getSteps(paramId);
                    model.put("recipe", recipe);
                    model.put("ingredients", ingredients);
                    model.put("steps", steps);
                    return new ModelAndView(model, "templates/layout.vtl");
                },
                new VelocityTemplateEngine());

        post(
                "/update/:id",
                (request, response) -> {
                    String paramId = request.params(":id");
                    Recipe recipeOld = recipeController.getRecipe(paramId);
                    User user = request.session().attribute("user");
                    if (!user.getUserId().equals(recipeOld.getUserName())) {
                        response.redirect("/index");
                        ;
                    }
                    if (recipeOld != null) {
                        recipeController.deleteRecipe(paramId);
                    }
                    stepController.deleteRecipeSteps(paramId);
                    ingredientController.deleteRecipeIngredients(paramId);
                    tagController.deleteTag(paramId);
                    request.attribute(
                            "org.eclipse.jetty.multipartConfig",
                            new MultipartConfigElement("/tmp"));
                    Part uploadedFile = null;
                    try {
                        uploadedFile = request.raw().getPart("final_image");
                    } catch (IOException | ServletException e) {
                        e.printStackTrace();
                    }
                    BufferedImage imBuff = ImageIO.read(uploadedFile.getInputStream());
                    String imageStr =
                            "data:image/png;base64, " + Tools.encodeToString(imBuff, "png");
                    Map<String, String[]> parameterMap = request.raw().getParameterMap();
                    String date = String.join(",", parameterMap.get("date"));
                    String title = String.join(",", parameterMap.get("title"));
                    String description = String.join(",", parameterMap.get("description"));
                    Recipe recipe =
                            new Recipe(
                                    date,
                                    title,
                                    user.getUserId(),
                                    description,
                                    imageStr,
                                    user.getfinalImage());
                    String recipeId = recipe.getRecipeId();
                    try {
                        recipeController.addRecipe(recipe);
                    } catch (Exception e) {
                        request.session().attribute("error", e);
                        response.redirect("/recipe");
                        System.out.println("hello");
                        return "";
                    }
                    Integer ingredient_max = Integer.valueOf(request.queryParams("ingredient-max"));
                    for (int i = 1; i < ingredient_max + 1; i++) {
                        String ingredientName =
                                String.join(",", parameterMap.get("ingredient_name" + i));
                        String amount = String.join(",", parameterMap.get("quantity" + i));
                        String unit = String.join(",", parameterMap.get("unit" + i));
                        Ingredient ingredient =
                                new Ingredient(ingredientName, recipeId, amount, unit);
                        ingredientController.addIngredient(ingredient);
                    }
                    Integer step_max = Integer.valueOf(request.queryParams("step-max"));
                    for (int i = 1; i < step_max + 1; i++) {
                        String stepDescription = String.join(",", parameterMap.get("step" + i));
                        Part stepFile = null;
                        try {
                            stepFile = request.raw().getPart("step_image" + i);
                        } catch (IOException | ServletException e) {
                            e.printStackTrace();
                        }
                        BufferedImage imBuffStep = ImageIO.read(stepFile.getInputStream());
                        String stepImage =
                                "data:image/png;base64, " + Tools.encodeToString(imBuffStep, "png");
                        Step step = new Step(recipeId, i, stepDescription, stepImage);
                        stepController.addStep(step);
                    }
                    Integer tag_max = Integer.valueOf(request.queryParams("tag-max"));
                    for (int i = 1; i < tag_max + 1; i++) {
                        String tagDescription = String.join(",", parameterMap.get("tag" + i));
                        Tag tag = new Tag(tagDescription, recipe.getRecipeId());
                        tagController.addTag(tag);
                    }
                    response.redirect("/index");
                    return "";
                });
        post(
                "/deletereview/:id",
                (request, response) -> {
                    String idx = request.params(":id");
                    String recipeId = request.queryParams("recipeIdInReview");
                    String reviewId = request.queryParams("review+" + idx);
                    Integer end = Integer.parseInt(request.queryParams("totalnumber"));
                    if (end == 0) {
                        response.redirect("/recipe/" + recipeId);
                    }
                    if (idx != null) {
                        reviewController.deleteReview(reviewId);
                        response.redirect("/recipe/" + recipeId);
                    }
                    return "";
                });

        post(
                "/delete/:id",
                (request, response) -> {
                    String paramId = request.params(":id");
                    Recipe recipe = recipeController.getRecipe(paramId);
                    User user = request.session().attribute("user");
                    if (!user.getUserId().equals(recipe.getUserName())) {
                        response.redirect("/index");
                    }
                    if (recipe != null) {
                        recipeController.deleteRecipe(paramId);
                        reviewController.deleteReviewByRecipeId(paramId);
                        tagController.deleteTag(paramId);
                        response.redirect("/index");
                    }
                    response.redirect("/index");
                    return "";
                });

        get(
                "/login",
                (request, response) -> {
                    Map<String, Object> model = new HashMap<String, Object>();
                    Collection<User> userList = userController.getUsers();
                    model.put("userList", userList);
                    if (request.session().attribute("loginError") != null) {
                        model.put(
                                "loginError", request.session().attribute("loginError").toString());
                        request.session().removeAttribute("loginError");
                    }
                    model.put("template", "templates/login.vtl");

                    return new ModelAndView(model, "templates/login.vtl");
                },
                new VelocityTemplateEngine());

        post(
                "/login",
                (request, response) -> {
                    String userName = request.queryParams("username");
                    String password = request.queryParams("password");
                    User user = userController.getUserByUserName(userName);
                    if (user != null && user.getPassWord().equals(password)) {
                        request.session().attribute(USER_SESSION_ID, user);
                        response.redirect("/index");
                        return user;
                    } else request.session().attribute("loginError", "Wrong Email or Password!");

                    response.redirect("/login");

                    return "";
                });

        post(
                "/signup",
                (request, response) -> {
                    request.attribute(
                            "org.eclipse.jetty.multipartConfig",
                            new MultipartConfigElement("/tmp"));
                    Part uploadedFile = null;

                    Map<String, String[]> parameterMap = request.raw().getParameterMap();
                    String userId = String.join(",", parameterMap.get("user_id"));
                    String userName = String.join(",", parameterMap.get("user_name"));
                    String passWord = String.join(",", parameterMap.get("password"));
                    String emailAddress = String.join(",", parameterMap.get("email"));
                    String slogan = String.join(",", parameterMap.get("Slogan"));
                    String status = String.join(",", parameterMap.get("Status"));
                    Boolean hasImage = true;
                    try {
                        uploadedFile = request.raw().getPart("final_image");
                    } catch (IOException | ServletException e) {
                        if (uploadedFile == null) {
                            hasImage = false;
                        }
                    }
                    String imageStr = "data:image/png;base64, ";
                    if (hasImage) {
                        BufferedImage imBuff = ImageIO.read(uploadedFile.getInputStream());
                        imageStr = "data:image/png;base64, " + Tools.encodeToString(imBuff, "png");
                    }

                    User user =
                            new User(
                                    userId,
                                    userName,
                                    passWord,
                                    emailAddress,
                                    slogan,
                                    status,
                                    imageStr);
                    try {
                        userController.addUser(user);
                        response.redirect("/index");

                    } catch (Exception e) {
                        // TODO: handle exception
                        request.session().attribute("error", e);
                        response.redirect("/signup");
                        return "";
                    }
                    return "";
                });

        get(
                "/profile/:id",
                (request, response) -> {
                    final String paramId = request.params(":id");
                    Map<String, Object> model = new HashMap<String, Object>();
                    if (request.session().attribute(USER_SESSION_ID) != null) {
                        User userIdLoggedIn = request.session().attribute(USER_SESSION_ID);
                        // System.out.print(curUserName);
                        if (paramId.equals(userIdLoggedIn.getUserId())) {
                            User usr = userController.getUser(userIdLoggedIn.getUserId());
                            if (usr == null) {
                                halt(404);
                            }

                            model.put("usr", usr);
                            return new ModelAndView(model, "templates/profile.vtl");
                        }
                    }
                    response.redirect("/index");
                    return new ModelAndView(model, "templates/layout.vtl");
                },
                new VelocityTemplateEngine());

        post(
                "/logout",
                (request, response) -> {
                    if (request.session().attribute(USER_SESSION_ID) != null) {
                        // User curUsr = request.session().attribute(USER_SESSION_ID);
                        request.session().removeAttribute(USER_SESSION_ID);
                        response.redirect("/index");
                        return "";
                    }
                    return "";
                });

        get(
                "/signup",
                (request, response) -> {
                    Map<String, Object> model = new HashMap<String, Object>();
                    if (request.session().attribute("error") != null) {
                        model.put("error", request.session().attribute("error").toString());
                        request.session().removeAttribute("error");
                    }
                    return new ModelAndView(model, "templates/signup.vtl");
                },
                new VelocityTemplateEngine());
    }
}
