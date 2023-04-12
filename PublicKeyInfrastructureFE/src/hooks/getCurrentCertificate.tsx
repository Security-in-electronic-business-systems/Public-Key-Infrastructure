import { useState, useEffect } from 'react';

export function useCurrentCertificate(): Certificate | null {
  const [currentCertificate, setCurrentCertificate] = useState<Certificate | null>(null);

  useEffect(() => {
    const storedCertificate = localStorage.getItem('currentCertificate');
    if (storedCertificate) {
      const parsedCertificate = JSON.parse(storedCertificate);
      setCurrentCertificate(parsedCertificate);
    }
  }, []);

  return currentCertificate;
}
