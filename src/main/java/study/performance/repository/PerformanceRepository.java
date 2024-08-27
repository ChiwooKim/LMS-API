package study.performance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.performance.domain.Performance;

public interface PerformanceRepository extends JpaRepository<Performance,Long> {
}
