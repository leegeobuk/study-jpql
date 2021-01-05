package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team barca = new Team();
            barca.setName("Barcelona");
            Team real = new Team();
            real.setName("Real Madrid");
            Team munich = new Team();
            munich.setName("Bayern Munich");

            Member messi = new Member();
            messi.setName("Lionel Messi");
            messi.setTeam(barca);
            Member rakitic = new Member();
            rakitic.setName("Ivan Rakitic");
            rakitic.setTeam(barca);

            Member modric = new Member();
            modric.setName("Luka Modric");
            modric.setTeam(real);

            Member pogba = new Member();
            pogba.setName("Paul Pogba");

            em.persist(barca);
            em.persist(real);
            em.persist(munich);

            em.persist(messi);
            em.persist(rakitic);
            em.persist(modric);
            em.persist(pogba);

            em.flush();
            em.clear();

            int resultCount = em.createNamedQuery("Member.updateAge")
                    .setParameter("age", 20)
                    .executeUpdate();
            List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
            System.out.println("resultCount = " + resultCount);
            for (Member member : result) {
                System.out.println("member = " + member);
            }


//            List<MemberDTO> memberDTOresult = em.createQuery("select new jpql.MemberDTO(m.name, m.age) from Member m", MemberDTO.class)
//                    .getResultList();
//            for (MemberDTO memberDTO : memberDTOresult) {
//                System.out.println("memberDTO.getName() = " + memberDTO.getName());
//                System.out.println("memberDTO.getAge() = " + memberDTO.getAge());
//            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Rollbacked");
            e.printStackTrace();
        }
        finally {
            em.close();
        }
        emf.close();
    }
}
