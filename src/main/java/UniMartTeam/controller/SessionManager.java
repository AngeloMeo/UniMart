package UniMartTeam.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionManager
{
   private HttpSession session = null;
   private int interval = 2500;

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

   public SessionManager(HttpServletRequest request, boolean createNew)
   {
      if(request != null)
      {
         this.session = request.getSession(createNew);
         this.session.setMaxInactiveInterval(interval);
      }
   }

   public void setMaxInactiveInterval(int interval)
   {
      if(session != null)
         session.setMaxInactiveInterval(interval);
   }

   public static void invalidateSession(HttpServletRequest request)
   {
      if(request != null)
         request.getSession().invalidate();
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
     if(checkParam(nameParam))
         return null;
      else
      {
         Object obj = session.getAttribute(nameParam);

         return (obj == null) ? null : obj;
      }
   }

   public boolean setAttribute(Object obj, String nameParam)
   {
      if(obj == null && checkParam(nameParam))
         return false;
      else
         session.setAttribute(nameParam, obj);

      return true;
   }

   public boolean removeAttribute(String nameParam)
   {
      if(checkParam(nameParam))
         return false;
      else
         session.removeAttribute(nameParam);

      return true;
   }

   public static Object getObjectFromSession(HttpServletRequest request, String nameParam)
   {
      SessionManager sessionManager = new SessionManager(request);

      if(sessionManager.checkParam(nameParam))
         return null;
      else
      {
         Object obj = sessionManager.getObjectFromSession(nameParam);

         return (obj == null) ? null : obj;
      }
   }

   private boolean checkParam(String nameParam)
   {
      return (session == null && nameParam != null && nameParam.isEmpty());
   }
}