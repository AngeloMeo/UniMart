package UniMartTeam.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionManager
{
   private HttpSession session = null;
   private int interval = 500;

   public SessionManager(HttpSession session)
   {
      this.session = session;
      this.session.setMaxInactiveInterval(interval);
   }

   public SessionManager(HttpServletRequest request)
   {
      if(request != null)
      {
         this.session = request.getSession();
         this.session.setMaxInactiveInterval(interval);
      }
   }

   public void setMaxInactiveInterval(int interval)
   {
      if(session != null)
         session.setMaxInactiveInterval(interval);
   }

   public HttpSession getSession()
   {
      return session;
   }

   public void setSession(HttpSession session)
   {
      this.session = session;
   }

   public Object getObjectFromSession(String nameParam)
   {
     if(session == null)
         return null;
      else
      {
         Object obj = session.getAttribute(nameParam);

         return (obj == null) ? null : obj;
      }
   }

   public boolean setAttribute(Object obj, String nomeParam)
   {
      if(obj == null && nomeParam.isEmpty() && session == null)
         return false;
      else
         session.setAttribute(nomeParam, obj);

      return true;
   }

   public boolean removeAttribute(String nomeParam)
   {
      if(nomeParam.isEmpty() && session == null)
         return false;
      else
         session.removeAttribute(nomeParam);

      return true;
   }

   public static Object getObjectFromSession(HttpServletRequest request, String nameParam)
   {
      HttpSession session = request.getSession();

      if(session == null)
         return null;
      else
      {
         Object obj = session.getAttribute(nameParam);

         return (obj == null) ? null : obj;
      }
   }
}