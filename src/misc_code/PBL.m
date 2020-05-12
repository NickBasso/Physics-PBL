clearvars
clc

%U - tensiunea(V)
%Uc - tensiunea pe con
%L - inductivitatea(H)
%C - capacitatea condensatorului(F)
%R - resistenta(Om)
%T - period(s)
%t - timp(s)
%d - decrement de atinuare
%omega - frecventa conturului 
%fi - faza de inceput(rad)
%a amplituda(m)
%q - 
%c -viteza luminii

t_max = 0.002;
t_step = 0.0000001;
t = [0:t_step:t_max];
%
R1 = 0;
U =150;
L =0.68;
C =0.04*10^(-6);
c = 299792458; 
fi = pi/12;
%

%{ 
R1 = 1
U = 150;
L = 1;
C = 1;
c = 299792458;
fi = 0.01
%}

omega0 = (L*C)^(-0.5)
R = omega0 * L + (omega0 * c)^(-1) + R1
d = R/(2*L)
omega = sqrt((omega0^2)-d^2)
T = 2*pi/omega
q0 = C*U
a0 = c * T
%
rad = ((omega*t+fi)*180)/pi;  %unghiu in radiane

a = a0*exp(-d*t)
figure(1);
plot(t,a);
hold on
grid on

q = a.*cos(rad)
figure(2);
plot(t,q);
hold on
grid on

Uc = U*exp(-d*t).*cos(rad)
figure(3);
plot(t,Uc);
hold on
grid on

Q = pi/d;
R_kr = 2 * sqrt(L / C);
log_koef = d*T;
disp(['Viteza       : ',num2str(v),' m/s '])
disp(['Acceleratia  : ',num2str(a),' m/s^2'])
disp(['Accel. rot   : ',num2str(a_rot),' m/s^2'])
disp(['Accel. ax.   : ',num2str(a_ax),' m/s^2'])