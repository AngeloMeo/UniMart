package UniMartTeam.model.Utils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Validator
{
   private final List<String> errors;
   private final HttpServletRequest request;
   private static final Pattern INT_PATTERN = Pattern.compile("^\\d+$");
   private static final Pattern DOUBLE_PATTERN = Pattern.compile("^(-)?(0|[1-9]\\d+)\\.\\d+$");
   private static final Pattern USERNAME_PATTERN = Pattern.compile("^([a-zA-Z\\s]){6,}$");
   private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9.!#$%&’*+/=?^_{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
   private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
   private static final Pattern CODICE_FISCALE_PATTERN = Pattern.compile("^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$");
   private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\d){10}$");

   public Validator(HttpServletRequest request)
   {
      errors = new ArrayList<>();
      this.request = request;
   }

   public boolean hasErrors()
   {
      return !errors.isEmpty();
   }

   public List<String> getErrors()
   {
      return errors;
   }

   private boolean gatherError(boolean condition, String message)
   {
      if (condition)
      {
         return true;
      }
      else
      {
         errors.add(message);
         return false;
      }
   }

   public boolean required(String value)
   {
      return value != null && !value.isBlank();
   }

   public boolean assertMatch(String value, Pattern regexp, String msg)
   {
      String param = request.getParameter(value);
      boolean condition = required(param) && regexp.matcher(param).matches();

      return gatherError(condition, msg);
   }

   public boolean assertInt(String value, String msg)
   {
      return assertMatch(value, INT_PATTERN, msg);
   }

   public boolean assertPassword(String value, String msg)
   {
      return assertMatch(value, PASSWORD_PATTERN, msg);
   }

   public boolean assertDouble(String value, String msg)
   {
      return assertMatch(value, DOUBLE_PATTERN, msg);
   }

   public boolean assertEmail(String value, String msg)
   {
      return assertMatch(value, EMAIL_PATTERN, msg);
   }

   public boolean assertPhone(String value, String msg)
   {
      return assertMatch(value, PHONE_PATTERN, msg);
   }

   public boolean assertUsername(String value, String msg)
   {
      return assertMatch(value, USERNAME_PATTERN, msg);
   }

   public boolean assertCF(String value, String msg)
   {
      return assertMatch(value, CODICE_FISCALE_PATTERN, msg);
   }

   public boolean assertInts(String values, String msg)
   {
      String[] params = request.getParameterValues(values);
      boolean allInt = Arrays.stream(params).allMatch(param -> INT_PATTERN.matcher(param).matches());
      return gatherError(allInt, msg);
   }

   public boolean assertSize(String first, String second, String msg)
   {
      String[] firstList = request.getParameterValues(first);
      String[] secondList = request.getParameterValues(second);
      return gatherError(firstList.length == secondList.length, msg);
   }

   public void reset()
   {
      errors.clear();
   }
}
