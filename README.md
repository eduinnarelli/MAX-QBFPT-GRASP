Problema MAX-QBF com Triplas Proibidas (MAX-QBFPT)
================================

Atividade 4 de MO824 - Tópicos em Otimização Combinatória.

**Grupo:**
  - Ana Clara Zoppi Serpa (165880)
  - Eduardo Barros Innarelli (170161)

Disciplina oferecida no 2º semestre de 2020 pelo professor [Fábio Luiz Usberti](https://www.ic.unicamp.br/~fusberti/).

[Instituto de Computação](http://ic.unicamp.br/) - [UNICAMP](http://www.unicamp.br/unicamp/) (Universidade Estadual de Campinas)

**Algumas observações sobre o código:**
- Nos construtores, colocar alfa entre 0 e 1 fará com que seja usado o método de construção padrão com este alfa. Colocar alfa > 1 fará com que seja usado reactive GRASP.
- Para usar bias, procurar o método setBias e remover as barras (//), assim a linha que configura o bias deixa de ser um comentário e passa a ser realmente executada.
